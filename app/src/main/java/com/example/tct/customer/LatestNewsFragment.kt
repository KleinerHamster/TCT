package com.example.tct.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.News
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class LatestNewsFragment: Fragment() {
    private lateinit var viewOfLayout: View

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList: ArrayList<News>
    private lateinit var myAdapter: AdapterNews
    private lateinit var db: FirebaseFirestore


    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_latest_news, container, false)

        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.news_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)

        newsArrayList = arrayListOf()//иницилизируем ArrayList новостей
        EventChangeListener()//вызываем метод для получения данных из Firestore Database
        myAdapter= AdapterNews(newsArrayList)
        recyclerView.adapter = myAdapter

        //устанавливаем действие нажатия на кнопку в списке новостей
        myAdapter.setOnClickListener(object: AdapterNews.onItemClickListener{
            override fun onItemClick(position: Int) {
                val title = newsArrayList[position].Title.toString()
                val about = newsArrayList[position].About.toString()
                val theNews = newsArrayList[position].TheNews.toString()
                val simpleDateFormat = SimpleDateFormat("HH:mm    dd.MM.yyyy")
                val dateNews = simpleDateFormat.format(newsArrayList[position].DateNews).toString()
                val photo = newsArrayList[position].Photo.toString()
                loadFragment(ReadNewsFragment.newInstance(title, about, theNews, dateNews, photo))
            }
        })
        return viewOfLayout
    }

    //метод для получения данных из Firestore Database
    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("news").orderBy("DateNews", Query.Direction.DESCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            newsArrayList.add(dc.document.toObject(News::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_information,fragment)
        transaction?.commit()
    }
}



