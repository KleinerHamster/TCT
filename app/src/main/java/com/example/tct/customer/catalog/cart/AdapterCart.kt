package com.example.tct.customer.catalog.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.account.AdapterOfficeInfo
import com.example.tct.model.OfficeInfo
import java.util.ArrayList

class AdapterCart(private val cartItemList: ArrayList<String>): RecyclerView.Adapter<AdapterCart.MyViewHolder>()  {

    //метод для создания связи ячейки и данных
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cart_name: TextView = itemView.findViewById(R.id.cart_name)
    }
    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.MyViewHolder {
        //создаем inflate с костомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_show, parent, false)

        //возвращаем созданую ячейку
        return AdapterCart.MyViewHolder(itemView)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
        return cartItemList.size
    }

    //метод cвязи новой ячейки и данных
    override fun onBindViewHolder(holder: AdapterCart.MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val item: String = cartItemList[position]

        //устанавливаем эл-т на view и модели данных
        holder.cart_name.text = item

    }
}