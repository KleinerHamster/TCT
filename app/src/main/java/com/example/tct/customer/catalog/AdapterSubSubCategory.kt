package com.example.tct.customer.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.example.tct.model.SubCategory
import com.example.tct.model.SubSubCategory
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterSubSubCategory(private val subSubCategoryList: ArrayList<SubSubCategory>): RecyclerView.Adapter<AdapterSubSubCategory.MyViewHolder>()  {

    //метод для создания связи ячейки и данных
    public class MyViewHolder(itemView: View, listener: AdapterSubSubCategory.onItemClickListener): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.subCategoryTitle)
        val photo: ImageView = itemView.findViewById(R.id.photoPipeSubCategory)
        init{
            photo.setOnClickListener{listener.onItemClick(adapterPosition)}
        }
    }

    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //создаем inflate с костомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sub_category, parent, false)

        //возвращаем созданую ячейку
        return MyViewHolder(itemView, mListener)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
        return subSubCategoryList.size
    }

    //метод создания новой ячейки
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val sub: SubSubCategory = subSubCategoryList[position]

        //устанавливаем эл-т на view и модели данных
        holder.title.text = sub.Title

        //загружаем фотографию через библиотеку Glide
        Glide
            .with(holder.photo.context)
            .load(sub.photo)
            .fitCenter()
            .placeholder(R.drawable.lg)
            .into(holder.photo)
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener=listener
    }
}