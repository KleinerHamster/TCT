package com.example.tct.customer.catalog

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tct.R
import com.example.tct.customer.catalog.cart.CartShowFragment
import com.example.tct.model.CartModel
import com.nex3z.notificationbadge.NotificationBadge

class CatalogCustomerFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private var model: CartModel? = null

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout = inflater.inflate(R.layout.fragment_catalog_customer, container, false)
        val go_to_order = viewOfLayout.findViewById<ImageView>(R.id.go_to_order)
        go_to_order.setOnClickListener {
            loadFragment1(CartShowFragment())
        }

        model = ViewModelProvider(requireActivity())[CartModel::class.java]
        val badge= viewOfLayout.findViewById<NotificationBadge>(R.id.badge)

        //через паттерн observe подписываемся на изменения
        model!!.cartDataList.observe(requireActivity(), Observer {
            val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
            if (itemCart.size>0) {
                badge.visibility=View.VISIBLE
                badge.setNumber(itemCart.size)
            }
            else{
                badge.visibility=View.GONE
            }
        })


        loadFragment(MainCatalogFragment())//загружаем каталог с категориями
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_main_catalog,fragment)
        transaction?.commit()
    }
    //метод для загрузки фрагмента
    private  fun loadFragment1(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_basket,fragment)
        transaction?.commit()
    }
}