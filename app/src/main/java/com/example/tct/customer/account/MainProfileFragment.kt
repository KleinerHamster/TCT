package com.example.tct.customer.account

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainProfileFragment : Fragment() {

    private lateinit var viewOfLayout: View
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_main_profile, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val emailUser = sharedPreferences!!.getString("email", "true")
        val phoneUser = sharedPreferences!!.getString("phone", "true")
        val nameUser = sharedPreferences!!.getString("name", "true")
        val branchUser = sharedPreferences!!.getString("branch", "true")

        //кнопка выхода из аккаунта
        val singOut = viewOfLayout.findViewById<TextView>(R.id.singOut)
        singOut.setOnClickListener {
            editor!!.clear()
            editor!!.commit()
            loadFragment(SingInCustomerFragment())
        }

        //кнопка выхода из аккаунта
        val goToUpdate = viewOfLayout.findViewById<TextView>(R.id.goToUpdate)
        goToUpdate.setOnClickListener {
            loadFragment(CustomerUpdateDataFragment())
        }

        val nameShow = viewOfLayout.findViewById<TextView>(R.id.nameShow)
        val phoneShow = viewOfLayout.findViewById<TextView>(R.id.phoneShow)
        val emailShow= viewOfLayout.findViewById<TextView>(R.id.emailShow)
        val branchShow= viewOfLayout.findViewById<TextView>(R.id.branchShow)
        //устанавливаем данные для просмотра
        emailShow.text=emailUser.toString()
        phoneShow.text=phoneUser.toString()
        nameShow.text=nameUser.toString()
        branchShow.text=branchUser.toString()
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }
}