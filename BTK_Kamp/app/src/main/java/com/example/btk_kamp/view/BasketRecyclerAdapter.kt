package com.example.btk_kamp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btk_kamp.R
import com.example.btk_kamp.model.Product
import kotlinx.android.synthetic.main.basket_recycler_row.view.*

class BasketRecyclerAdapter(val basketList: List<Product>) :
    RecyclerView.Adapter<BasketRecyclerAdapter.BasketViewHolder>() {
    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.basket_recycler_row, parent, false)
        return BasketViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.itemView.basketProductNameText.text = basketList.get(position).name
        holder.itemView.basketProductPriceText.text = "Fiyat : ₺ ${basketList.get(position).price}"
        holder.itemView.basketProductQuantityText.text = "Adet: ${basketList.get(position).quantity}"
        Glide.with(holder.itemView.context).load(basketList.get(position).url)
            .into(holder.itemView.basketImageView)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }
}