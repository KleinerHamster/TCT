package com.example.tct.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.example.tct.customer.catalog.BasketCustomerFragment
import com.example.tct.customer.news.InformationCustomerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragmentCustomer: Fragment() {

    lateinit var bottomNav : BottomNavigationView
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_customer_main, container, false)
        loadFragment(BasketCustomerFragment())

        bottomNav=viewOfLayout.findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.selectedItemId = R.id.basket

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.news -> {
                    loadFragment(InformationCustomerFragment())
                    true
                }
                R.id.basket -> {
                    loadFragment(BasketCustomerFragment())
                    true
                }
                R.id.account -> {
                    loadFragment(AccountCustomerFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }

        return viewOfLayout
    }


    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_customer,fragment)
        transaction?.commit()
    }
}