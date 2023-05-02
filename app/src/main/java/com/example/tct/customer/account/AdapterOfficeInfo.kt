package com.example.tct.customer.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.catalog.AdapterMainCategory
import com.example.tct.model.MainCategory
import com.example.tct.model.OfficeInfo
import java.util.ArrayList

class AdapterOfficeInfo(private val officeInfoList: ArrayList<OfficeInfo>): RecyclerView.Adapter<AdapterOfficeInfo.MyViewHolder>() {

    //метод для создания связи ячейки и данных
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val address: TextView = itemView.findViewById(R.id.addressOffice)
        val phone: TextView = itemView.findViewById(R.id.phoneOffice)
        val emailOffice: TextView = itemView.findViewById(R.id.emailOffice)
        val workHours: TextView = itemView.findViewById(R.id.workHours)
        val cityOffice: TextView = itemView.findViewById(R.id.cityOffice)
    }

    //метод создания новой ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //создаем inflate с костомной разметкой
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_about_oficce, parent, false)

        //возвращаем созданую ячейку
        return MyViewHolder(itemView)
    }

    //метод для получения кол-ва эл-тов списка
    override fun getItemCount(): Int {
        return officeInfoList.size
    }

    //метод cвязи новой ячейки и данных
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //получаем по номеру позиции данные
        val category: OfficeInfo = officeInfoList[position]

        //устанавливаем эл-т на view и модели данных
        holder.address.text = category.Address
        holder.phone.text = category.Phone
        holder.emailOffice.text = category.Email
        holder.workHours.text = category.WorkHours
        holder.cityOffice.text = category.City
    }
}