package com.example.btk_kamp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btk_kamp.R
import com.example.btk_kamp.model.Product
import com.example.btk_kamp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_products.*


class ProductsFragment : Fragment(), ProductRecyclerAdapter.Listener {
    private val productViewModel: ProductViewModel by activityViewModels()
    private var productRecyclerAdapter: ProductRecyclerAdapter? = null

    override fun onCreateView( //hangi viewle çalışacagımızı belirtir
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    //uygulamada fragmentlar oluşturulduktan sonra yapılacak işlemler için yazılan fonksiyon
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager =
            GridLayoutManager(activity?.baseContext, 2) //yanyana kaç ürün gösterilecegini belirler
        productViewModel.downloadData()
        productViewModel.productList.observe(viewLifecycleOwner, {
            productRecyclerAdapter = ProductRecyclerAdapter(it, this)
            recyclerView.adapter = productRecyclerAdapter
        })
    }

    override fun onItemClick(product: Product) {
        productViewModel.addToBasket(product)
    }
}