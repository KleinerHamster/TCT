package com.example.tct.customer.catalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.SubCategory
import com.example.tct.model.SubSubCategory
import com.google.firebase.firestore.*

private const val ARG_PARAM1 = "subCategory"
private const val ARG_PARAM2 = "docMain"
private const val ARG_PARAM3 = "docIdPipe"
private const val ARG_PARAM4 = "mainCategory"

class SubSubCatalogFragment : Fragment() {
    private lateinit var viewOfLayout: View

    private var subCategory: String? = null
    private var docMain: String? = null
    private var docIdPipe: String? = null
    private var mainCategory: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var subSubCategoryArrayList: ArrayList<SubSubCategory>
    private lateinit var myAdapter: AdapterSubSubCategory
    private lateinit var db: FirebaseFirestore

    //параметры view, которые будем менять -
    //инициализировать в конструкторе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCategory = it.getString(ARG_PARAM1)
            docMain = it.getString(ARG_PARAM2)
            docIdPipe = it.getString(ARG_PARAM3)
            mainCategory= it.getString(ARG_PARAM4)
        }
    }

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sub_sub_catalog, container, false)
        //устанавливаем заголовок выбранной главной категории
        val subCategoryTitle = viewOfLayout.findViewById<TextView>(R.id.subCategoryTitle)
        subCategoryTitle.text=subCategory

        //кнопка для возращения к списку новостей
        val backToSubCategory = viewOfLayout.findViewById<ImageView>(R.id.backToSubCategory)
        backToSubCategory.setOnClickListener{
            loadFragment(SubCatalogFragment.newInstance(mainCategory.toString(), docMain.toString()))
        }

        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.sub_sub_category_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(viewOfLayout.context, 2)
        subSubCategoryArrayList = arrayListOf()//иницилизируем ArrayList подкатегорий
        EventChangeListener()//вызываем метод для получения данных из Firestore Database
        myAdapter= AdapterSubSubCategory(subSubCategoryArrayList)
        recyclerView.adapter = myAdapter
        //устанавливаем действие нажатия на кнопку в списке новостей
        myAdapter.setOnClickListener(object: AdapterSubSubCategory.onItemClickListener{
            override fun buyItemClick(position: Int) {
                Toast.makeText(
                    requireActivity(),
                    "купить "+subSubCategoryArrayList[position].Title.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return viewOfLayout
    }

    companion object {
        //иницилизируем данные из бандла, если он не нулевой
        //проверим, есть ли нужные ключи
        //получаем данные
        @JvmStatic
        fun newInstance(subCategory: String, docMain: String, docIdPipe: String, mainCategory: String) =
            SubSubCatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, subCategory)
                    putString(ARG_PARAM2, docMain)
                    putString(ARG_PARAM3, docIdPipe)
                    putString(ARG_PARAM4, mainCategory)
                }
            }
    }
    //метод для получения данных из Firestore Database
    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("catalog/"+docMain.toString()+"/information/"+docIdPipe.toString()
                +"/subcatalog").orderBy("order", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            subSubCategoryArrayList.add(dc.document.toObject(SubSubCategory::class.java))
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