package com.example.btk_kamp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btk_kamp.R
import com.example.btk_kamp.model.Product
import kotlinx.android.synthetic.main.recycler_row.view.*

class ProductRecyclerAdapter(val productList: List<Product>, private val listener: Listener) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ProductHolder>() {
    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return ProductHolder(view)
    }

    //hangi görünümde nelerin görünecegini seçiyoruz
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.itemView.productName.text = productList.get(position).name
        holder.itemView.productPrice.text = productList.get(position).price
        // urlyi image içine koyuyor
        Glide.with(holder.itemView.context).load(productList.get(position).url)
            .into(holder.itemView.productImage)
        holder.itemView.addBasketButton.setOnClickListener {
            Toast.makeText(
                it.context,
                "Sepete eklendi ${productList.get(position).name}",
                Toast.LENGTH_LONG
            ).show()
            listener.onItemClick(productList.get(position))
        }
    }

    //kaçtane ürün olucagını belirliyoruz
    override fun getItemCount(): Int {
        return productList.size //kaç tane ürünümüz varsa verir
    }

    //tıklandıgında yapılacak işlemi belirttigimiz bir arayüzdür
    interface Listener {
        fun onItemClick(product: Product)
    }
}