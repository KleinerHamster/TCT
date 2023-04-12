package com.example.tct.customer

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.example.tct.model.News
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class AdapterNews(private val newsList: ArrayList<News>): RecyclerView.Adapter<AdapterNews.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

     interface onItemClickListener{

         fun onItemClick(position: Int)
     }

    fun setOnClickListener(listener: onItemClickListener){
        mListener=listener
    }
    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.MyViewHolder {

        //создаем inflate с костомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lats_news, parent, false)

        //возвращаем созданую ячейку
        return MyViewHolder(itemView, mListener)
    }

    //метод создания новой ячейки
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterNews.MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val news: News = newsList[position]

        //устанавливаем эл-т на view и модели данных
        holder.title.text = news.Title
        holder.about.text = news.About
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        holder.date.text = simpleDateFormat.format(news.DateNews)

        //загружаем фотографию через библиотеку Glide
        Glide
            .with(holder.photo.context)
            .load(news.Photo)
            .fitCenter()
            .placeholder(R.drawable.lg)
            .into(holder.photo)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
        return newsList.size
    }

    //метод для создания связи ячейки и данных
    public class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.titleNews)
        val about: TextView = itemView.findViewById(R.id.descriptionNews)
        val date: TextView = itemView.findViewById(R.id.dateNews)
        val photo: ImageView = itemView.findViewById(R.id.photoNews)
        val readButton: Button = itemView.findViewById(R.id.button_read_news)
      init{
          readButton.setOnClickListener { listener.onItemClick(adapterPosition) }
      }
    }
}