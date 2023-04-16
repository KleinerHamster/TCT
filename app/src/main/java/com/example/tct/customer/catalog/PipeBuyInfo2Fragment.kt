package com.example.tct.customer.catalog

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "mainCategory"
private const val ARG_PARAM2 = "docMain"
private const val ARG_PARAM3 = "docIdPipe"
private const val ARG_PARAM4 = "namePipe"

class PipeBuyInfo2Fragment : Fragment() {
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

    //метод при отображение фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_pipe_buy_info2, container, false)

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
        val photoPipe1 = viewOfLayout.findViewById<ImageView>((R.id.photoPipe1))

        //текст характиристик
        val characteristics1 = viewOfLayout.findViewById<TextView>((R.id.characteristics1))
        val characteristics2 = viewOfLayout.findViewById<TextView>((R.id.characteristics2))
        val characteristics3 = viewOfLayout.findViewById<TextView>((R.id.characteristics3))
        val characteristics4 = viewOfLayout.findViewById<TextView>((R.id.characteristics4))
        val characteristics5 = viewOfLayout.findViewById<TextView>((R.id.characteristics5))
        val characteristics6 = viewOfLayout.findViewById<TextView>((R.id.characteristics6))
        val characteristics7 = viewOfLayout.findViewById<TextView>((R.id.characteristics7))
        val characteristics8 = viewOfLayout.findViewById<TextView>((R.id.characteristics8))
        //фото характиристик
        val  characteristicsPhoto1 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto1))
        val  characteristicsPhoto2 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto2))
        val  characteristicsPhoto3 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto3))
        val  characteristicsPhoto4 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto4))
        val  characteristicsPhoto5 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto5))
        val  characteristicsPhoto6 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto6))
        val  characteristicsPhoto7 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto7))
        val  characteristicsPhoto8 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto8))
        val lineForC8 = viewOfLayout.findViewById<LinearLayout>((R.id.lineForC8))
        val lineForC7 = viewOfLayout.findViewById<LinearLayout>((R.id.lineForC7))

        //иницилизируем кнопки для просмотра под-подкатегорий труб
        val  design1 = viewOfLayout.findViewById<Button>((R.id.design1))
        val  design2 = viewOfLayout.findViewById<Button>((R.id.design2))
        val  design3 = viewOfLayout.findViewById<Button>((R.id.design3))
        val  design4 = viewOfLayout.findViewById<Button>((R.id.design4))
        //иницилизируем фото для просмотра выбранной трйбы под-подкатегории
        val photoPipe2 = viewOfLayout.findViewById<ImageView>((R.id.photoPipe2))
        val photoPipe3 = viewOfLayout.findViewById<ImageView>((R.id.photoPipe3))
        val descriptionDesign1 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign1))
        val descriptionDesign2 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign2))
        val descriptionDesign3 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign3))
        val descriptionDesign4 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign4))
        val descriptionAboutDesign = viewOfLayout.findViewById<TextView>((R.id.descriptionAboutDesign))

        //иницилизируем текст для преимуществ
        val advantages1 = viewOfLayout.findViewById<TextView>((R.id.advantages1))
        val advantages2 = viewOfLayout.findViewById<TextView>((R.id.advantages2))
        val advantages3 = viewOfLayout.findViewById<TextView>((R.id.advantages3))
        val advantages4 = viewOfLayout.findViewById<TextView>((R.id.advantages4))
        val advantages5 = viewOfLayout.findViewById<TextView>((R.id.advantages5))
        val advantages6 = viewOfLayout.findViewById<TextView>((R.id.advantages6))
        //иницилизируем фото для преимуществ
        val advantages1Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages1Photo))
        val advantages2Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages2Photo))
        val advantages3Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages3Photo))
        val advantages4Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages4Photo))
        val advantages5Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages5Photo))
        val advantages6Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages6Photo))

        //иницилизируем текст для области применения
        val areaOfUse1 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse1)
        val areaOfUse2 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse2)
        val areaOfUse3 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse3)
        val areaOfUse4 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse4)
        val areaOfUse5 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse5)
        val areaOfUse6 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse6)
        //иницилизируем фото для области применения
        val areaOfUsePhoto1 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto1))
        val areaOfUsePhoto2 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto2))
        val areaOfUsePhoto3 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto3))
        val areaOfUsePhoto4 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto4))
        val areaOfUsePhoto5 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto5))
        val areaOfUsePhoto6 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto6))

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
                    //загружаем изображение трубы через библиотеку Glide
                    Glide.with(viewOfLayout).load(document.getString("MainPhoto"))
                        .fitCenter().placeholder(R.drawable.lg).into(photoPipe1)

                    //характеристики товара
                    characteristics1.text = document.getString("Characteristics1")
                    characteristics2.text = document.getString("Characteristics2")
                    characteristics3.text = document.getString("Characteristics3")
                    characteristics4.text = document.getString("Characteristics4")
                    characteristics5.text = document.getString("Characteristics5")
                    characteristics6.text = document.getString("Characteristics6")
                    if (document.getString("Characteristics7").equals("нет")){
                        characteristics7.visibility = View.GONE
                        lineForC7.visibility = View.GONE
                        characteristicsPhoto7.visibility = View.GONE
                        characteristics8.visibility = View.GONE
                        lineForC8.visibility = View.GONE
                        characteristicsPhoto8.visibility = View.GONE
                    }
                    else{
                        characteristics7.text = document.getString("Characteristics7")
                        characteristics8.text = document.getString("Characteristics8")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Characteristics7Photo")).fitCenter()
                            .into(characteristicsPhoto7)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Characteristics8Photo")).fitCenter()
                            .into(characteristicsPhoto8)
                    }
                    //загружаем изображение характеристик трубы через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics1Photo")).fitCenter()
                        .into(characteristicsPhoto1)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics2Photo")).fitCenter()
                        .into(characteristicsPhoto2)
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

                    //устанавливаем текст преимуществ
                    advantages1.text = document.getString("Advantages1")
                    advantages2.text = document.getString("Advantages2")
                    //загружаем изображения преимушеств через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages1Photo")).centerCrop()
                        .into(advantages1Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages2Photo")).centerCrop()
                        .into(advantages2Photo)
                    if(document.getString("Advantages3").equals("нет")){
                        advantages3.visibility = View.GONE
                        advantages4.visibility = View.GONE
                        advantages5.visibility = View.GONE
                        advantages6.visibility = View.GONE
                        advantages3Photo.visibility = View.GONE
                        advantages4Photo.visibility = View.GONE
                        advantages5Photo.visibility = View.GONE
                        advantages6Photo.visibility = View.GONE
                    }
                    else if(document.getString("Advantages4").equals("нет")){
                        advantages3.text = document.getString("Advantages3")
                        advantages4.visibility = View.GONE
                        advantages5.visibility = View.GONE
                        advantages6.visibility = View.GONE
                        advantages4Photo.visibility = View.GONE
                        advantages5Photo.visibility = View.GONE
                        advantages6Photo.visibility = View.GONE
                        //загружаем изображения преимушеств через библиотеку Glide
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages3Photo")).centerCrop()
                            .into(advantages3Photo)
                    }
                    else {
                        advantages3.text = document.getString("Advantages3")
                        advantages4.text = document.getString("Advantages4")
                        advantages5.text = document.getString("Advantages5")
                        advantages6.text = document.getString("Advantages6")
                        //загружаем изображения преимушеств через библиотеку Glide
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages3Photo")).centerCrop()
                            .into(advantages3Photo)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages4Photo")).centerCrop()
                            .into(advantages4Photo)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages5Photo")).centerCrop()
                            .into(advantages5Photo)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages6Photo")).centerCrop()
                            .into(advantages6Photo)
                    }

                    //области применения загружаем текст и фото
                    areaOfUse1.text = document.getString("Area1")
                    areaOfUse2.text = document.getString("Area2")
                    areaOfUse3.text = document.getString("Area3")
                    Glide.with(viewOfLayout)
                        .load(document.getString("Area1Photo")).centerCrop().into(areaOfUsePhoto1)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Area2Photo")).centerCrop().into(areaOfUsePhoto2)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Area3Photo")).centerCrop().into(areaOfUsePhoto3)
                    if(document.getString("Area4").equals("нет")){
                        areaOfUse4.visibility = View.GONE
                        areaOfUse5.visibility = View.GONE
                        areaOfUse6.visibility = View.GONE
                        areaOfUsePhoto4.visibility = View.GONE
                        areaOfUsePhoto5.visibility = View.GONE
                        areaOfUsePhoto6.visibility = View.GONE
                    }
                    else if(document.getString("Area5").equals("нет")){
                        areaOfUse4.text = document.getString("Area4")
                        areaOfUse5.visibility = View.GONE
                        areaOfUse6.visibility = View.GONE
                        areaOfUsePhoto5.visibility = View.GONE
                        areaOfUsePhoto6.visibility = View.GONE
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area4Photo")).centerCrop()
                            .into(areaOfUsePhoto4)
                    }
                    else if(document.getString("Area6").equals("нет")){
                        areaOfUse6.visibility = View.GONE
                        areaOfUsePhoto6.visibility = View.GONE
                        areaOfUse4.text = document.getString("Area4")
                        areaOfUse5.text = document.getString("Area5")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area4Photo")).centerCrop().into(areaOfUsePhoto4)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area5Photo")).centerCrop().into(areaOfUsePhoto5)
                    }
                    else{
                        areaOfUse4.text = document.getString("Area4")
                        areaOfUse5.text = document.getString("Area5")
                        areaOfUse6.text = document.getString("Area6")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area4Photo")).centerCrop().into(areaOfUsePhoto4)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area5Photo")).centerCrop().into(areaOfUsePhoto5)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area6Photo")).centerCrop().into(areaOfUsePhoto6)
                    }

                    //по умолчанию показ первой конструкции
                    design1.text=document.getString("Design1")
                    descriptionDesign1.text=document.getString("Design1Des1")
                    descriptionDesign2.text=document.getString("Design1Des2")
                    descriptionDesign3.text=document.getString("Design1Des3")
                    descriptionDesign4.text=document.getString("Design1Des4")
                    descriptionAboutDesign.text=document.getString("Design1About")
                    Glide.with(viewOfLayout)
                        .load(document.getString("Design1Photo")).fitCenter()
                        .into(photoPipe2)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Design1Photo1")).fitCenter()
                        .into(photoPipe3)

                    //проверяем кол-во видов конструкций у трубы
                    if(document.getString("Design2").equals("нет")){
                        design2.visibility = View.GONE
                        design3.visibility = View.GONE
                        design4.visibility = View.GONE
                    }
                    else if(document.getString("Design3").equals("нет")){
                        design2.text=document.getString("Design2")
                        design3.visibility = View.GONE
                        design4.visibility = View.GONE
                    }
                    else if(document.getString("Design4").equals("нет")){
                        design2.text=document.getString("Design2")
                        design3.text=document.getString("Design3")
                        design4.visibility = View.GONE
                    }
                    else{
                        design2.text=document.getString("Design2")
                        design3.text=document.getString("Design3")
                        design4.text=document.getString("Design4")
                    }

                    //кнопка для просмотра первой трубы
                    design1.setOnClickListener {
                        //меняем стиль у кнопок
                        design1.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design1.setTextColor(getResources().getColor(R.color.text_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design1Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design1Des1")
                        descriptionDesign2.text=document.getString("Design1Des2")
                        descriptionDesign3.text=document.getString("Design1Des3")
                        descriptionDesign4.text=document.getString("Design1Des4")
                        descriptionAboutDesign.text=document.getString("Design1About")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design1Photo1")).fitCenter()
                            .into(photoPipe3)
                    }

                    //кнопка для просмотра второй трубы
                    design2.setOnClickListener {
                        //меняем стиль у кнопок
                        design2.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design2.setTextColor(getResources().getColor(R.color.text_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design2Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design2Des1")
                        descriptionDesign2.text=document.getString("Design2Des2")
                        descriptionDesign3.text=document.getString("Design2Des3")
                        descriptionDesign4.text=document.getString("Design2Des4")
                        descriptionAboutDesign.text=document.getString("Design2About")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design2Photo1")).fitCenter()
                            .into(photoPipe3)
                    }

                    //кнопка для просмотра третей трубы
                    design3.setOnClickListener {
                        //меняем стиль у кнопок
                        design3.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design3.setTextColor(getResources().getColor(R.color.text_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design3Photo")).fitCenter().into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design3Des1")
                        descriptionDesign2.text=document.getString("Design3Des2")
                        descriptionDesign3.text=document.getString("Design3Des3")
                        descriptionDesign4.text=document.getString("Design3Des4")
                        descriptionAboutDesign.text=document.getString("Design3About")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design3Photo1")).fitCenter().into(photoPipe3)
                    }

                    //кнопка для просмотра четвертой трубы
                    design4.setOnClickListener {
                        //меняем стиль у кнопок
                        design4.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design4.setTextColor(getResources().getColor(R.color.text_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design4Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design4Des1")
                        descriptionDesign2.text=document.getString("Design4Des2")
                        descriptionDesign3.text=document.getString("Design4Des3")
                        descriptionDesign4.text=document.getString("Design4Des4")
                        descriptionAboutDesign.text=document.getString("Design4About")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design4Photo1")).fitCenter()
                            .into(photoPipe3)
                    }

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
            PipeBuyInfo2Fragment().apply {
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