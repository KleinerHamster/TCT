package com.example.tct.customer.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.example.tct.customer.news.NewsCustomerFragment

class InformationCustomerFragment: Fragment() {
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_information_customer, container, false)
        loadFragment(NewsCustomerFragment())
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_information,fragment)
        transaction?.commit()
    }
}