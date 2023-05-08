package com.example.tct.customer.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.CustomerOrder
import java.text.SimpleDateFormat

class CustomerOrderAdapter(private val customerOrderList: ArrayList<CustomerOrder>):  RecyclerView.Adapter<CustomerOrderAdapter.MyViewHolder>(){
    //метод для создания связи ячейки и данных
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateOrder: TextView = itemView.findViewById(R.id.dateOrder)
        val commentOrder: TextView = itemView.findViewById(R.id.commentOrder)
        val goodsOrderRV:RecyclerView = itemView.findViewById(R.id.goodsOrderRV)
    }

    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //создаем inflate с кастомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_watch_order, parent, false)
        //возвращаем созданую ячейку
        return MyViewHolder(itemView)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
       return customerOrderList.size
    }

    //метод cвязи новой ячейки и данных
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val order: CustomerOrder = customerOrderList[position]

        //устанавливаем эл-т на view и модели данных
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        holder.dateOrder.text = simpleDateFormat.format(order.DateOrder)
        holder.commentOrder.text = order.Comment
        holder.goodsOrderRV.setHasFixedSize(true)
        holder.goodsOrderRV.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = GoodsAdapter(order.Goods!!)
        holder.goodsOrderRV.adapter = adapter
    }
}
