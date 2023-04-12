package com.example.tct.customer.catalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.AdapterNews
import com.example.tct.customer.ReadNewsFragment
import com.example.tct.model.MainCategory
import com.example.tct.model.News
import com.google.firebase.firestore.*
import java.text.SimpleDateFormat

class MainCatalogFragment : Fragment() {
    private lateinit var viewOfLayout: View

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryArrayList: ArrayList<MainCategory>
    private lateinit var myAdapter: AdapterMainCategory
    private lateinit var db: FirebaseFirestore
    private lateinit var mainCategory: String
    private lateinit var docMain: String

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_main_catalog, container, false)
        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.main_category_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)

        categoryArrayList = arrayListOf()//иницилизируем ArrayList новостей
        EventChangeListener()//вызываем метод для получения данных из Firestore Database
        myAdapter= AdapterMainCategory(categoryArrayList)
        recyclerView.adapter = myAdapter
        //устанавливаем действие нажатия на кнопку в списке новостей
        myAdapter.setOnClickListener(object: AdapterMainCategory.onItemClickListener{
            override fun onItemClick(position: Int) {
                mainCategory=categoryArrayList[position].Title.toString()
                docMain=categoryArrayList[position].docId.toString()
                loadFragment(SubCatalogFragment.newInstance(mainCategory, docMain))
            }
        })
        return viewOfLayout
    }

    //метод для получения данных из Firestore Database
    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("catalog").orderBy("order", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            categoryArrayList.add(dc.document.toObject(MainCategory::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_main_catalog,fragment)
        transaction?.commit()
    }
}