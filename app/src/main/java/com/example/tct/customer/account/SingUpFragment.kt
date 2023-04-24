package com.example.tct.customer.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.tct.R


class SingUpFragment : Fragment() {
    private lateinit var viewOfLayout: View
    val first:Boolean=true
    val branch = arrayOf("Выбирите филиал","Санкт-Петербург","Москва", "Екатеринбург","Краснодар",
        "Тюмень", "Пермь", "Новосибирск", "Уфа", "Самара", "Челябинск")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sing_up, container, false)

        val spinner = viewOfLayout.findViewById<Spinner>(R.id.spinner_branch)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner, branch)
            adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(requireContext(),  branch[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        return viewOfLayout
    }
}