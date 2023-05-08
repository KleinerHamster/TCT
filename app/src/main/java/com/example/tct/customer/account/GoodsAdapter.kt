package com.example.tct.customer.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.CustomerOrder
import java.text.SimpleDateFormat

class GoodsAdapter(private val goodsList: ArrayList<String>):  RecyclerView.Adapter<GoodsAdapter.MyViewHolder>() {
    //метод для создания связи ячейки и данных
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val goodsOrder: TextView = itemView.findViewById(R.id.goodsOrder)
    }

    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //создаем inflate с кастомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_goods_order, parent, false)
        //возвращаем созданую ячейку
        return MyViewHolder(itemView)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
       return goodsList.size
    }

    //метод cвязи новой ячейки и данных
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val good: String = goodsList[position]

        //устанавливаем эл-т на view и модели данных
        holder.goodsOrder.text = good
    }
}