package com.example.tct.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.example.tct.customer.catalog.CatalogCustomerFragment

class BasketCustomerFragment: Fragment() {
    private lateinit var viewOfLayout: View

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_customer_basket, container, false)

        loadFragment(CatalogCustomerFragment())
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_basket,fragment)
        transaction?.commit()
    }
}