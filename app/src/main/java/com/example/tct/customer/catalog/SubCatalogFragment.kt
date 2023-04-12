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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.NewsCustomerFragment
import com.example.tct.model.MainCategory
import com.example.tct.model.SubCategory
import com.google.firebase.firestore.*


private const val ARG_PARAM1 = "mainCategory"
private const val ARG_PARAM2 = "docMain"


class SubCatalogFragment : Fragment() {
    private lateinit var viewOfLayout: View

    private var mainCategory: String? = null
    private var docMain: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var subCategoryArrayList: ArrayList<SubCategory>
    private lateinit var myAdapter: AdapterSubCategory
    private lateinit var db: FirebaseFirestore

    //параметры view, которые будем менять -
    //инициализировать в конструкторе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCategory = it.getString(ARG_PARAM1)
            docMain = it.getString(ARG_PARAM2)
        }
    }

    //метод при создание и отображение активности
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sub_catalog, container, false)

        //устанавливаем заголовок выбранной главной категории
        val mainCategoryTitle = viewOfLayout.findViewById<TextView>(R.id.mainCategoryTitle)
        mainCategoryTitle.text=mainCategory

        //кнопка для возращения к списку новостей
        val backToMainCategory = viewOfLayout.findViewById<ImageView>(R.id.backToMainCategory)
        backToMainCategory.setOnClickListener{
            loadFragment(MainCatalogFragment())
        }

        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.sub_category_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(viewOfLayout.context, 2)

        subCategoryArrayList = arrayListOf()//иницилизируем ArrayList подкатегорий
        EventChangeListener()//вызываем метод для получения данных из Firestore Database
        myAdapter=AdapterSubCategory(subCategoryArrayList)
        recyclerView.adapter = myAdapter
        //устанавливаем действие нажатия на кнопку в списке новостей
        myAdapter.setOnClickListener(object: AdapterSubCategory.onItemClickListener{
            override fun onItemClick(position: Int) {
                //взависимости от категории трубы отображаем фрагмент
                if (subCategoryArrayList[position].TypeOfFragment.toString()=="pipe1"){
                    loadFragment(PipeBuyInfoFragment.newInstance(mainCategory.toString(),
                        docMain.toString(),
                        subCategoryArrayList[position].docId.toString(),
                        subCategoryArrayList[position].Title.toString()))

                }
                else if (subCategoryArrayList[position].TypeOfFragment.toString()=="pipe2"){
                    loadFragment(PipeBuyInfo1Fragment.newInstance(mainCategory.toString(),
                        docMain.toString(),
                        subCategoryArrayList[position].docId.toString(),
                        subCategoryArrayList[position].Title.toString()))
                }
                else if(subCategoryArrayList[position].TypeOfFragment.toString()=="pipe3"){
                    loadFragment(PipeBuyInfo2Fragment.newInstance(mainCategory.toString(),
                        docMain.toString(),
                        subCategoryArrayList[position].docId.toString(),
                        subCategoryArrayList[position].Title.toString()))
                }
                else if(subCategoryArrayList[position].TypeOfFragment.toString()=="pipe4"){
                    loadFragment(SubSubCatalogFragment.newInstance(
                        subCategoryArrayList[position].Title.toString(),
                        docMain.toString(),
                        subCategoryArrayList[position].docId.toString(),
                        mainCategory.toString()))
                }
                else {
                    Toast.makeText(
                        requireActivity(),
                        subCategoryArrayList[position].Title.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        return viewOfLayout
    }

    companion object {
        //иницилизируем данные из бандла, если он не нулевой
        //проверим, есть ли нужные ключи
        //получаем данные
        @JvmStatic
        fun newInstance(mainCategory: String, docMain: String) =
            SubCatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mainCategory)
                    putString(ARG_PARAM2, docMain)
                }
            }
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_main_catalog,fragment)
        transaction?.commit()
    }

    //метод для получения данных из Firestore Database
    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("catalog/"+docMain.toString()+"/information").orderBy("order", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {

                            subCategoryArrayList.add(dc.document.toObject(SubCategory::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }
}