package com.example.tct.customer.news

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tct.R
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso

class InformationCompanyFragment: Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var fStore: FirebaseFirestore

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_information_company, container, false)

        val photoInfoAboutCompany = viewOfLayout.findViewById<ImageView>(R.id.photoInfoAboutCompany)
        val aboutFirst = viewOfLayout.findViewById<TextView>(R.id.aboutFirst)
        val aboutSecond = viewOfLayout.findViewById<TextView>(R.id.aboutSecond)
        val aboutThird = viewOfLayout.findViewById<TextView>(R.id.aboutThird)
        val missionPhoto = viewOfLayout.findViewById<ImageView>(R.id.missionPhoto)
        val mission = viewOfLayout.findViewById<TextView>(R.id.mission)
        val valueFirst = viewOfLayout.findViewById<TextView>(R.id.valueFirst)
        val valueSecond = viewOfLayout.findViewById<TextView>(R.id.valueSecond)
        val valueThird = viewOfLayout.findViewById<TextView>(R.id.valueThird)
        val valueForth = viewOfLayout.findViewById<TextView>(R.id.valueForth)
        val motto = viewOfLayout.findViewById<TextView>(R.id.motto)

        fStore = FirebaseFirestore.getInstance()//подключению к FireStore
        //подключаемся и получаем информацию из FireStore
        val dr: DocumentReference = fStore.collection("information").document("PAqu9PHSIVkuTgyyfrqh")
        dr.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {

                    //загружаем фотографию через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("photoBackground")).fitCenter().centerCrop()
                        .error(R.drawable.lg)
                        .placeholder(R.drawable.lg)
                        .into(photoInfoAboutCompany)

                    //устанавливаем ифнормацию из FireStore
                    aboutFirst.text=document.getString("aboutFirst")
                    aboutSecond.text=document.getString("aboutSecond")
                    aboutThird.text=document.getString("aboutThird")

                    //загружаем картинку для миссии компании через библиотеку Glide
                    Glide
                        .with(viewOfLayout)
                        .load(document.getString("missionPhoto")).centerCrop()
                        .placeholder(R.drawable.lg).error(R.drawable.lg)
                        .into(missionPhoto)
                    //загружаем текст миссии
                    mission.text=document.getString("mission")

                    //загружаем ценности
                    valueFirst.text=document.getString("valueFirst")
                    valueSecond.text=document.getString("valueSecond")
                    valueThird.text=document.getString("valueThird")
                    valueForth.text=document.getString("valueForth")

                    //загружаем текст девиза
                    motto.text=document.getString("motto")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            } else {
                Log.d(
                    ContentValues.TAG,
                    "get failed with ",
                    task.exception
                )
            }
        }

        return viewOfLayout
    }
}