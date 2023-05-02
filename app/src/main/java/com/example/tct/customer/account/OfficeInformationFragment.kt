package com.example.tct.customer.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.news.AdapterNews
import com.example.tct.model.News
import com.example.tct.model.OfficeInfo
import com.google.firebase.firestore.*

class OfficeInformationFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var officeArrayList: ArrayList<OfficeInfo>
    private lateinit var myAdapter: AdapterOfficeInfo
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_office_information, container, false)
        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.officeInfo_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)

        officeArrayList = arrayListOf()//иницилизируем ArrayList новостей
        EventChangeListener()//вызываем метод для получения данных из Firestore Database
        myAdapter= AdapterOfficeInfo(officeArrayList)
        recyclerView.adapter = myAdapter

        //кнопка возвращения назад
        val backToProfile = viewOfLayout.findViewById<ImageView>(R.id.backToProfile)
        backToProfile.setOnClickListener {
            loadFragment(MainProfileFragment())
        }
        return viewOfLayout
    }
    //метод для получения данных из Firestore Database
    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("office").orderBy("order")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            officeArrayList.add(dc.document.toObject(OfficeInfo::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }
    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }
}