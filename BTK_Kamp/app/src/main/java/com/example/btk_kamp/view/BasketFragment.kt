package com.example.btk_kamp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btk_kamp.R
import com.example.btk_kamp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_basket.*


class BasketFragment : Fragment() {
    private val productViewModel: ProductViewModel by activityViewModels()
    private var basketRecyclerAdapter: BasketRecyclerAdapter? = null

    //private val swipeCallBack= object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) //sola dogru kaydırılırsa silinecek
    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        //iki yönden bir tanesine kayrıdılırsa silinecek
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }
        //kaydırılırsa yapılacak işlem yazılır
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition //kaydırılıan posizyon
            if(basketRecyclerAdapter!=null){
                val selectedProdct= basketRecyclerAdapter!!.basketList.get(layoutPosition)
                productViewModel.deleteProductFromBasket(selectedProdct)
                basketRecyclerAdapter!!.notifyDataSetChanged()
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basketRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext)
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(basketRecyclerView)
        productViewModel.basket.observe(viewLifecycleOwner, {
            basketRecyclerAdapter = BasketRecyclerAdapter(it)
            basketRecyclerView.adapter = basketRecyclerAdapter

        })
        productViewModel.totalBasket.observe(viewLifecycleOwner, {
            totalBaskettext.text = "Toplam Sepet : ₺ $it"
        })
    }
}