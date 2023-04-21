package com.example.tct.customer.catalog

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.google.firebase.firestore.*

private const val ARG_PARAM1 = "mainCategory"
private const val ARG_PARAM2 = "docMain"
private const val ARG_PARAM3 = "docIdPipe"
private const val ARG_PARAM4 = "namePipe"


class PipeBuyInfoFragment : Fragment() {
    private lateinit var viewOfLayout: View

    private var mainCategory: String? = null
    private var docMain: String? = null
    private var docIdPipe: String? = null
    private var namePipe: String? = null


    private lateinit var db: FirebaseFirestore

    //параметры view, которые будем менять -
    //инициализировать в конструкторе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCategory = it.getString(ARG_PARAM1)
            docMain = it.getString(ARG_PARAM2)
            docIdPipe = it.getString(ARG_PARAM3)
            namePipe = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_pipe_water_supply1, container, false)


        //устанавливаем название выбранного продукта
        val mainCategoryTitle = viewOfLayout.findViewById<TextView>(R.id.mainCategoryTitle)
        mainCategoryTitle.text = mainCategory

        //кнопка для возращения к списку новостей
        val backToSubCategory = viewOfLayout.findViewById<ImageView>(R.id.backToSubCategory)
        backToSubCategory.setOnClickListener{
            loadFragment(SubCatalogFragment.newInstance(mainCategory.toString(), docMain.toString()))
        }

        val pipeFullName = viewOfLayout.findViewById<TextView>(R.id.pipeFullName)
        val descriptionPipe1 = viewOfLayout.findViewById<TextView>(R.id.descriptionPipe1)
        val description1Pipe1 = viewOfLayout.findViewById<TextView>(R.id.description1Pipe1)
        val photoPipe1 = viewOfLayout.findViewById<ImageView>((R.id.photoPipe1))
        val delivery1 = viewOfLayout.findViewById<TextView>(R.id.delivery1)
        val delivery2 = viewOfLayout.findViewById<TextView>(R.id.delivery2)
        val deliveryPhoto1 = viewOfLayout.findViewById<ImageView>((R.id.deliveryPhoto1))
        val deliveryPhoto2 = viewOfLayout.findViewById<ImageView>((R.id.deliveryPhoto2))
        val areaOfUse1 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse1)
        val areaOfUse2 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse2)
        val areaOfUse3 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse3)
        val areaOfUse4 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse4)
        val areaOfUsePhoto1 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto1))
        val areaOfUsePhoto2 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto2))
        val areaOfUsePhoto3 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto3))
        val areaOfUsePhoto4 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto4))
        val advantages1 = viewOfLayout.findViewById<TextView>((R.id.advantages1))
        val advantages2 = viewOfLayout.findViewById<TextView>((R.id.advantages2))
        val advantages3 = viewOfLayout.findViewById<TextView>((R.id.advantages3))
        val advantages4 = viewOfLayout.findViewById<TextView>((R.id.advantages4))
        val advantages5 = viewOfLayout.findViewById<TextView>((R.id.advantages5))
        val advantages6 = viewOfLayout.findViewById<TextView>((R.id.advantages6))
        val advantages7 = viewOfLayout.findViewById<TextView>((R.id.advantages7))
        val advantages1Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages1Photo))
        val advantages2Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages2Photo))
        val advantages3Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages3Photo))
        val advantages4Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages4Photo))
        val advantages5Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages5Photo))
        val advantages6Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages6Photo))
        val advantages7Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages7Photo))

        val characteristics1 = viewOfLayout.findViewById<TextView>((R.id.characteristics1))
        val characteristics2 = viewOfLayout.findViewById<TextView>((R.id.characteristics2))
        val characteristics3 = viewOfLayout.findViewById<TextView>((R.id.characteristics3))
        val characteristics4 = viewOfLayout.findViewById<TextView>((R.id.characteristics4))
        val characteristics5 = viewOfLayout.findViewById<TextView>((R.id.characteristics5))
        val characteristics6 = viewOfLayout.findViewById<TextView>((R.id.characteristics6))
        val characteristics7 = viewOfLayout.findViewById<TextView>((R.id.characteristics7))

        val  characteristicsPhoto1 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto1))
        val  characteristicsPhoto2 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto2))
        val  characteristicsPhoto3 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto3))
        val  characteristicsPhoto4 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto4))
        val  characteristicsPhoto5 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto5))
        val  characteristicsPhoto6 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto6))
        val  characteristicsPhoto7 = viewOfLayout.findViewById<ImageView>((R.id. characteristicsPhoto7))
        val  lineC1 = viewOfLayout.findViewById<LinearLayout>((R.id.lineC1))


        db = FirebaseFirestore.getInstance()//подключению к FireStore
        //подключаемся и получаем информацию из FireStore
        val dr: DocumentReference = db.collection("catalog/"+docMain.toString()+
                "/information/"+ docIdPipe.toString()+"/pipeBuy").document("PEGOST")
        dr.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    pipeFullName.text = document.getString("FullTitle")
                    //основная информация о товаре
                    descriptionPipe1.text = document.getString("MainInfo1")
                    description1Pipe1.text = document.getString("MainInfo2")

                    //текст о доставке
                    delivery1.text = document.getString("AboutDelivery1")
                    delivery2.text = document.getString("AboutDelivery2")

                    //характеристики товара
                    characteristics1.text = document.getString("Characteristics1")
                    if (document.getString("Characteristics2").equals("нет")){
                        characteristics2.visibility = View.GONE
                        characteristicsPhoto2.visibility = View.GONE
                        lineC1.visibility = View.GONE
                    }
                    else{
                        characteristics2.text = document.getString("Characteristics2")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Characteristics2Photo")).fitCenter()
                            .into(characteristicsPhoto2)
                    }
                    characteristics3.text = document.getString("Characteristics3")
                    characteristics4.text = document.getString("Characteristics4")
                    characteristics5.text = document.getString("Characteristics5")
                    characteristics6.text = document.getString("Characteristics6")
                    characteristics7.text = document.getString("Characteristics7")

                    //текст области применения
                    areaOfUse1.text = document.getString("Area1")
                    //загружаем изображение области применения через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Area1Photo")).fitCenter()
                        .into(areaOfUsePhoto1)
                    if (document.getString("Area2").equals("нет")){
                        areaOfUse2.visibility = View.GONE
                        areaOfUse3.visibility = View.GONE
                        areaOfUse4.visibility = View.GONE
                        areaOfUsePhoto2.visibility = View.GONE
                        areaOfUsePhoto3.visibility = View.GONE
                        areaOfUsePhoto4.visibility = View.GONE
                    }
                    else{
                        areaOfUse2.text = document.getString("Area2")
                        areaOfUse3.text = document.getString("Area3")
                        areaOfUse4.text = document.getString("Area4")
                        //загружаем изображение области применения через библиотеку Glide
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area2Photo")).fitCenter()
                            .into(areaOfUsePhoto2)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area3Photo")).fitCenter()
                            .into(areaOfUsePhoto3)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area4Photo")).fitCenter()
                            .into(areaOfUsePhoto4)
                    }

                    //устанавливаем текст преимуществ
                    advantages1.text = document.getString("Advantages1")
                    advantages2.text = document.getString("Advantages2")
                    advantages3.text = document.getString("Advantages3")
                    advantages4.text = document.getString("Advantages4")
                    advantages5.text = document.getString("Advantages5")
                    advantages6.text = document.getString("Advantages6")
                    advantages7.text = document.getString("Advantages7")

                    //загружаем изображение трубы через библиотеку Glide
                    Glide
                        .with(viewOfLayout)
                        .load(document.getString("MainPhoto")).fitCenter()
                        .placeholder(R.drawable.lg)
                        .into(photoPipe1)

                    //загружаем изображение для доставки через библиотеку Glide
                    Glide
                        .with(viewOfLayout)
                        .load(document.getString("AboutDelivery1Photo")).fitCenter()
                        .into(deliveryPhoto1)
                    Glide
                        .with(viewOfLayout)
                        .load(document.getString("AboutDelivery2Photo")).fitCenter()
                        .into(deliveryPhoto2)

                    //загружаем изображение характеристик трубы через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics1Photo")).fitCenter()
                        .into(characteristicsPhoto1)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics3Photo")).fitCenter()
                        .into(characteristicsPhoto3)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics4Photo")).fitCenter()
                        .into(characteristicsPhoto4)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics5Photo")).fitCenter()
                        .into(characteristicsPhoto5)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics6Photo")).fitCenter()
                        .into(characteristicsPhoto6)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics7Photo")).fitCenter()
                        .into(characteristicsPhoto7)

                    //загружаем изображения преимушеств через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages1Photo")).fitCenter()
                        .into(advantages1Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages2Photo")).fitCenter()
                        .into(advantages2Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages3Photo")).fitCenter()
                        .into(advantages3Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages4Photo")).fitCenter()
                        .into(advantages4Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages5Photo")).fitCenter()
                        .into(advantages5Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages6Photo")).fitCenter()
                        .into(advantages6Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages7Photo")).fitCenter()
                        .into(advantages7Photo)
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

    companion object {
        //иницилизируем данные из бандла, если он не нулевой
        //проверим, есть ли нужные ключи
        //получаем данные
        @JvmStatic
        fun newInstance(mainCategory: String, docMain: String, docIdPipe: String, namePipe: String) =
            PipeBuyInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mainCategory)
                    putString(ARG_PARAM2, docMain)
                    putString(ARG_PARAM3, docIdPipe)
                    putString(ARG_PARAM4, namePipe)
                }
            }
    }


    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_main_catalog,fragment)
        transaction?.commit()
    }
}