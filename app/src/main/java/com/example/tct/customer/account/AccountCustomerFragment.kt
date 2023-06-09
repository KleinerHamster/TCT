package com.example.tct.customer.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.example.tct.customer.account.SingInCustomerFragment

class AccountCustomerFragment: Fragment() {
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_customer_account, container, false)
        val sharedPreferences = requireActivity().getSharedPreferences("user data", AppCompatActivity.MODE_PRIVATE)
        val login = sharedPreferences.getString("LOGIN", null)
        if(login.equals("true"))
            loadFragment(MainProfileFragment())
        else
            loadFragment(SingInCustomerFragment())
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }
}