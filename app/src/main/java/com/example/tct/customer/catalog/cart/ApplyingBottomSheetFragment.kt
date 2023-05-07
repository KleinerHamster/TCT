package com.example.tct.customer.catalog.cart

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tct.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ApplyingBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var viewOfLayout: View
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_applying_bottom_sheet, container, false)
        //иницилизируем данные
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()

        val nameUser = viewOfLayout.findViewById<TextView>(R.id.nameUser)
        nameUser.text = sharedPreferences!!.getString("name", "true")
        val phoneUser = viewOfLayout.findViewById<TextView>(R.id.phoneUser)
        phoneUser.text = sharedPreferences!!.getString("phone", "true")
        val emailUser = viewOfLayout.findViewById<TextView>(R.id.emailUser)
        emailUser.text = sharedPreferences!!.getString("email", "true")



        //sharedPreferences!!.getString("userId", "false").toString()
        return viewOfLayout
    }
}