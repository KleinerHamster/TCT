package com.example.tct.customer.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.MainCategory
import java.util.ArrayList

class AdapterMainCategory(private val mainCategoryList: ArrayList<MainCategory>): RecyclerView.Adapter<AdapterMainCategory.MyViewHolder>() {

    //метод для создания связи ячейки и данных
    public class MyViewHolder(itemView: View, listener: AdapterMainCategory.onItemClickListener): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.main_category_Title)
        init{
            title.setOnClickListener { listener.onItemClick(adapterPosition) }
        }
    }

    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMainCategory.MyViewHolder {
        //создаем inflate с костомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_main_category, parent, false)

        //возвращаем созданую ячейку
        return AdapterMainCategory.MyViewHolder(itemView, mListener)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
        return mainCategoryList.size
    }

    //метод создания новой ячейки
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val category: MainCategory = mainCategoryList[position]

        //устанавливаем эл-т на view и модели данных
        holder.title.text = category.Title
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener=listener
    }
}