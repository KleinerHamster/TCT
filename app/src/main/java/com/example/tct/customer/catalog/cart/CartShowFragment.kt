package com.example.tct.customer.catalog.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.account.AdapterOfficeInfo
import com.example.tct.model.CartModel
import com.google.gson.Gson
import java.util.ArrayList


class CartShowFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private var model: CartModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: AdapterCart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_cart_show, container, false)


        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.cart_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)
        //получаем данные из  ViewModel
        //получаем данные из  ViewModel
        model = ViewModelProvider(requireActivity())[CartModel::class.java]
        //получаем данные из  ViewModel
        val news: List<String?> = model!!.get().value!!
        myAdapter= AdapterCart(news as ArrayList<String>)
        recyclerView.adapter = myAdapter
        return viewOfLayout
    }
}
