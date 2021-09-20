package com.example.btk_kamp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btk_kamp.model.Product
import com.example.btk_kamp.service.ProductAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {
    private var job: Job? = null
    val productList = MutableLiveData<List<Product>>()
    val basket = MutableLiveData<List<Product>>()
    val totalBasket = MutableLiveData<Int>()

    fun downloadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
        /*
        * Network kolu yapılıyorsa : IO
        * Kullanıcı arayüzüyle ilgili bir işlem yapılıyorsa : Main
        * Cpu yu zorluyacak bir işlem yapılıyorsa : Default
        */
        job = viewModelScope.launch(context = Dispatchers.IO) {
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        productList.value = it
                    }
                }
            }
        }
    }

    fun addToBasket(product: Product) {
        if (basket.value != null) {
            val arrayList = ArrayList(basket.value)
            if (arrayList.contains(product)) {
                val indexOfFirst = arrayList.indexOfFirst { it == product }
                val relatedProduct = arrayList.get(indexOfFirst)
                relatedProduct.quantity += 1
                basket.value = arrayList
            } else {
                product.quantity += 1
                arrayList.add(product)
                basket.value = arrayList
            }
        } else {
            val arrayList = arrayListOf(product)
            product.quantity += 1
            basket.value = arrayList
        }
        basket.value.let {
            refreshTotalValue(it!!)
        }
    }

    private fun refreshTotalValue(listOfProduct: List<Product>) {
        var total = 0
        listOfProduct.forEach { product ->
            val price = product.price.toIntOrNull() // string degerleri integere çevirmek için
            price?.let {
                val quantity = product.quantity
                val revenue = quantity * it
                total += revenue
            }
        }
        totalBasket.value = total
    }

    fun deleteProductFromBasket(product: Product) {
        if (basket.value != null) {
            val arrayList = ArrayList(basket.value)
            arrayList.remove(product)
            basket.value = arrayList
            refreshTotalValue(arrayList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}