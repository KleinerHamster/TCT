package com.example.tct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tct.customer.MainFragmentCustomer
import com.example.tct.model.CartModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(MainFragmentCustomer())
        val model: CartModel = ViewModelProvider(this)[CartModel::class.java]
        var itemCart: ArrayList<String> = model.get().value!! as ArrayList

        val sharedPreferences = getSharedPreferences("user data", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("order", null)
        if (json != null) {
            val type: Type = object: TypeToken<ArrayList<String>>() {}.type
            itemCart = gson.fromJson(json, type)
            model.setData(itemCart)
        }
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper,fragment)
        transaction.commit()
    }

}