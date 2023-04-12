package com.example.tct.customer.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tct.R

class CatalogCustomerFragment : Fragment() {
    private lateinit var viewOfLayout: View

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout = inflater.inflate(R.layout.fragment_catalog_customer, container, false)
        loadFragment(MainCatalogFragment())//загружаем каталог с категориями
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_main_catalog,fragment)
        transaction?.commit()
    }
}