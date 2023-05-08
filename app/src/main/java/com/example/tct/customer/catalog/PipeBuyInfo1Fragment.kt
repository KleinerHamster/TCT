package com.example.tct.customer.catalog

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tct.R
import com.example.tct.model.CartModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


private const val ARG_PARAM1 = "mainCategory"
private const val ARG_PARAM2 = "docMain"
private const val ARG_PARAM3 = "docIdPipe"
private const val ARG_PARAM4 = "namePipe"


class PipeBuyInfo1Fragment : Fragment() {
    private lateinit var viewOfLayout: View

    private var mainCategory: String? = null
    private var docMain: String? = null
    private var docIdPipe: String? = null
    private var namePipe: String? = null
    private lateinit var db: FirebaseFirestore
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private var model: CartModel? = null

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
        viewOfLayout = inflater.inflate(R.layout.fragment_pipe_buy_info1, container, false)

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

        //текст характиристик
        val characteristics1 = viewOfLayout.findViewById<TextView>((R.id.characteristics1))
        val characteristics2 = viewOfLayout.findViewById<TextView>((R.id.characteristics2))
        val characteristics3 = viewOfLayout.findViewById<TextView>((R.id.characteristics3))
        val characteristics4 = viewOfLayout.findViewById<TextView>((R.id.characteristics4))
        val characteristics5 = viewOfLayout.findViewById<TextView>((R.id.characteristics5))
        val characteristics6 = viewOfLayout.findViewById<TextView>((R.id.characteristics6))
        val characteristics7 = viewOfLayout.findViewById<TextView>((R.id.characteristics7))
        //фото характиристик
        val  characteristicsPhoto1 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto1))
        val  characteristicsPhoto2 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto2))
        val  characteristicsPhoto3 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto3))
        val  characteristicsPhoto4 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto4))
        val  characteristicsPhoto5 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto5))
        val  characteristicsPhoto6 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto6))
        val  characteristicsPhoto7 = viewOfLayout.findViewById<ImageView>((R.id.characteristicsPhoto7))

        //иницилизируем кнопки для просмотра под-подкатегорий труб
        val  design1 = viewOfLayout.findViewById<Button>((R.id.design1))
        val  design2 = viewOfLayout.findViewById<Button>((R.id.design2))
        val  design3 = viewOfLayout.findViewById<Button>((R.id.design3))
        val  design4 = viewOfLayout.findViewById<Button>((R.id.design4))
        val  design5 = viewOfLayout.findViewById<Button>((R.id.design5))
        val  design6 = viewOfLayout.findViewById<Button>((R.id.design6))
        val  design7 = viewOfLayout.findViewById<Button>((R.id.design7))
        //иницилизируем фото для просмотра выбранной трйбы под-подкатегории
        val photoPipe2 = viewOfLayout.findViewById<ImageView>((R.id.photoPipe2))
        val descriptionDesign1 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign1))
        val descriptionDesign2 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign2))
        val descriptionDesign3 = viewOfLayout.findViewById<TextView>((R.id.descriptionDesign3))

        val areaOfUse1 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse1)
        val areaOfUse2 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse2)
        val areaOfUse3 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse3)
        val areaOfUse4 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse4)
        val areaOfUse5 = viewOfLayout.findViewById<TextView>(R.id.areaOfUse5)
        val areaOfUsePhoto1 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto1))
        val areaOfUsePhoto2 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto2))
        val areaOfUsePhoto3 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto3))
        val areaOfUsePhoto4 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto4))
        val areaOfUsePhoto5 = viewOfLayout.findViewById<ImageView>((R.id.areaOfUsePhoto5))

        val advantages1 = viewOfLayout.findViewById<TextView>((R.id.advantages1))
        val advantages2 = viewOfLayout.findViewById<TextView>((R.id.advantages2))
        val advantages3 = viewOfLayout.findViewById<TextView>((R.id.advantages3))
        val advantages4 = viewOfLayout.findViewById<TextView>((R.id.advantages4))
        val advantages5 = viewOfLayout.findViewById<TextView>((R.id.advantages5))
        val advantages6 = viewOfLayout.findViewById<TextView>((R.id.advantages6))
        val advantages7 = viewOfLayout.findViewById<TextView>((R.id.advantages7))
        val advantages8 = viewOfLayout.findViewById<TextView>((R.id.advantages8))
        val advantages9 = viewOfLayout.findViewById<TextView>((R.id.advantages9))
        val advantages10 = viewOfLayout.findViewById<TextView>((R.id.advantages10))
        val advantages11 = viewOfLayout.findViewById<TextView>((R.id.advantages11))
        val advantages12 = viewOfLayout.findViewById<TextView>((R.id.advantages12))
        val advantages13 = viewOfLayout.findViewById<TextView>((R.id.advantages13))
        val advantages14 = viewOfLayout.findViewById<TextView>((R.id.advantages14))

        val advantages1Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages1Photo))
        val advantages2Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages2Photo))
        val advantages3Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages3Photo))
        val advantages4Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages4Photo))
        val advantages5Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages5Photo))
        val advantages6Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages6Photo))
        val advantages7Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages7Photo))
        val advantages8Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages8Photo))
        val advantages9Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages9Photo))
        val advantages10Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages10Photo))
        val advantages11Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages11Photo))
        val advantages12Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages12Photo))
        val advantages13Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages13Photo))
        val advantages14Photo = viewOfLayout.findViewById<ImageView>((R.id.advantages14Photo))
        val lineForC4 = viewOfLayout.findViewById<LinearLayout>((R.id.lineForC4))
        val lineForC5 = viewOfLayout.findViewById<LinearLayout>((R.id.lineForC5))
        val branch: ArrayList<String> = arrayListOf("Выберите тип")
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
                    characteristics2.text = document.getString("Characteristics2")
                    characteristics3.text = document.getString("Characteristics3")
                    characteristics6.text = document.getString("Characteristics6")
                    characteristics7.text = document.getString("Characteristics7")
                    if (document.getString("Characteristics4").equals("нет")){
                        characteristics4.visibility = View.GONE
                        lineForC4.visibility = View.GONE
                        characteristicsPhoto4.visibility = View.GONE
                    }
                    else{
                        characteristics4.text = document.getString("Characteristics4")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Characteristics4Photo")).fitCenter()
                            .into(characteristicsPhoto4)
                    }
                    if (document.getString("Characteristics5").equals("нет")){
                        characteristics5.visibility = View.GONE
                        lineForC5.visibility = View.GONE
                        characteristicsPhoto5.visibility = View.GONE
                    }
                    else{
                        characteristics5.text = document.getString("Characteristics5")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Characteristics5Photo")).centerCrop()
                            .into(characteristicsPhoto5)
                    }


                    //устанавливаем текст преимуществ
                    advantages1.text = document.getString("Advantages1")
                    advantages2.text = document.getString("Advantages2")
                    advantages3.text = document.getString("Advantages3")
                    advantages4.text = document.getString("Advantages4")
                    advantages5.text = document.getString("Advantages5")
                    advantages6.text = document.getString("Advantages6")
                    advantages7.text = document.getString("Advantages7")
                    advantages8.text = document.getString("Advantages8")
                    advantages9.text = document.getString("Advantages9")
                    if(document.getString("Advantages10").equals("нет")){
                        advantages10.visibility = View.GONE
                        advantages10Photo.visibility = View.GONE
                        advantages11.visibility = View.GONE
                        advantages11Photo.visibility = View.GONE
                        advantages12.visibility = View.GONE
                        advantages12Photo.visibility = View.GONE
                        advantages13.visibility = View.GONE
                        advantages13Photo.visibility = View.GONE
                        advantages14.visibility = View.GONE
                        advantages14Photo.visibility = View.GONE
                    }
                    else  if(document.getString("Advantages12").equals("нет")){
                        advantages12.visibility = View.GONE
                        advantages12Photo.visibility = View.GONE
                        advantages13.visibility = View.GONE
                        advantages13Photo.visibility = View.GONE
                        advantages14.visibility = View.GONE
                        advantages14Photo.visibility = View.GONE
                        advantages10.text = document.getString("Advantages10")
                        advantages11.text = document.getString("Advantages11")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages10Photo")).fitCenter()
                            .into(advantages10Photo)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages11Photo")).fitCenter()
                            .into(advantages11Photo)
                    }
                    else{
                        advantages10.text = document.getString("Advantages10")
                        advantages11.text = document.getString("Advantages11")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages10Photo")).fitCenter()
                            .into(advantages10Photo)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages11Photo")).fitCenter()
                            .into(advantages11Photo)

                        advantages12.text = document.getString("Advantages12")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages12Photo")).fitCenter()
                            .into(advantages12Photo)

                        advantages13.text = document.getString("Advantages13")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages13Photo")).fitCenter()
                            .into(advantages13Photo)

                        advantages14.text = document.getString("Advantages14")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Advantages14Photo")).fitCenter()
                            .into(advantages14Photo)
                    }


                    //области применения
                    areaOfUse1.text = document.getString("Area1")
                    if(document.getString("Area2").equals("нет")){
                        areaOfUse2.visibility = View.GONE
                        areaOfUsePhoto2.visibility = View.GONE
                        areaOfUse3.visibility = View.GONE
                        areaOfUsePhoto3.visibility = View.GONE
                        areaOfUse4.visibility = View.GONE
                        areaOfUsePhoto4.visibility = View.GONE
                        areaOfUse5.visibility = View.GONE
                        areaOfUsePhoto5.visibility = View.GONE
                    }
                    else if (!document.getString("Area5").equals("нет")){
                        areaOfUse2.text = document.getString("Area2")
                        areaOfUse3.text = document.getString("Area3")
                        areaOfUse4.text = document.getString("Area4")
                        areaOfUse5.text = document.getString("Area5")
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area2Photo")).fitCenter()
                            .into(areaOfUsePhoto2)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area3Photo")).fitCenter()
                            .into(areaOfUsePhoto3)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area4Photo")).fitCenter()
                            .into(areaOfUsePhoto4)
                        Glide.with(viewOfLayout)
                            .load(document.getString("Area5Photo")).fitCenter()
                            .into(areaOfUsePhoto5)
                    }
                    else if (document.getString("Area5").equals("нет")){
                        areaOfUse5.visibility = View.GONE
                        areaOfUsePhoto5.visibility = View.GONE
                        areaOfUse2.text = document.getString("Area2")
                        areaOfUse3.text = document.getString("Area3")
                        areaOfUse4.text = document.getString("Area4")
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

                    //загружаем изображение области применения через библиотеку Glide
                    Glide.with(viewOfLayout)
                        .load(document.getString("Area1Photo")).fitCenter()
                        .into(areaOfUsePhoto1)


                    //загружаем изображение трубы через библиотеку Glide
                    Glide
                        .with(viewOfLayout)
                        .load(document.getString("MainPhoto"))
                        .fitCenter()
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
                        .load(document.getString("Characteristics2Photo")).fitCenter()
                        .into(characteristicsPhoto2)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics3Photo")).fitCenter()
                        .into(characteristicsPhoto3)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics5Photo")).fitCenter()
                        .into(characteristicsPhoto5)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics6Photo")).fitCenter()
                        .into(characteristicsPhoto6)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Characteristics7Photo")).fitCenter()
                        .into(characteristicsPhoto7)


                    Glide.with(viewOfLayout)
                        .load(document.getString("Design1Photo")).fitCenter()
                        .into(photoPipe2)


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
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages8Photo")).fitCenter()
                        .into(advantages8Photo)
                    Glide.with(viewOfLayout)
                        .load(document.getString("Advantages9Photo")).fitCenter()
                        .into(advantages9Photo)


                    design1.text=document.getString("Design1")
                    design2.text=document.getString("Design2")
                    design3.text=document.getString("Design3")
                    design4.text=document.getString("Design4")
                    design5.text=document.getString("Design5")
                    design6.text=document.getString("Design6")
                    design7.text=document.getString("Design7")
                    branch.add(document.getString("Design1").toString())
                    branch.add(document.getString("Design2").toString())
                    branch.add(document.getString("Design3").toString())
                    branch.add(document.getString("Design4").toString())
                    descriptionDesign1.text=document.getString("Design1Des1")
                    descriptionDesign2.text=document.getString("Design1Des2")
                    descriptionDesign3.text=document.getString("Design1Des3")

                    //кнопка для просмотра первой трубы
                    design1.setOnClickListener {
                        //меняем стиль у кнопок
                        design1.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design1.setTextColor(getResources().getColor(R.color.text_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))


                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design1Photo")).fitCenter()
                            .into(photoPipe2)

                        descriptionDesign1.text=document.getString("Design1Des1")
                        descriptionDesign2.text=document.getString("Design1Des2")
                        descriptionDesign3.text=document.getString("Design1Des3")
                    }

                    //кнопка для просмотра второй трубы
                    design2.setOnClickListener {
                        //меняем стиль у кнопок
                        design2.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design2.setTextColor(getResources().getColor(R.color.text_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design2Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design2Des1")
                        descriptionDesign2.text=document.getString("Design2Des2")
                        descriptionDesign3.text=document.getString("Design2Des3")
                    }

                    //кнопка для просмотра третей трубы
                    design3.setOnClickListener {
                        //меняем стиль у кнопок
                        design3.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design3.setTextColor(getResources().getColor(R.color.text_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design3Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design3Des1")
                        descriptionDesign2.text=document.getString("Design3Des2")
                        descriptionDesign3.text=document.getString("Design3Des3")
                    }

                    //кнопка для просмотра четвертой трубы
                    design4.setOnClickListener {
                        //меняем стиль у кнопок
                        design4.setBackgroundResource(R.drawable.default_outlined_button_style)
                        design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                        design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                        //меняем цвет текста
                        design4.setTextColor(getResources().getColor(R.color.text_color))
                        design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        //загружаем фото и описание
                        Glide.with(viewOfLayout)
                            .load(document.getString("Design4Photo")).fitCenter()
                            .into(photoPipe2)
                        descriptionDesign1.text=document.getString("Design4Des1")
                        descriptionDesign2.text=document.getString("Design4Des2")
                        descriptionDesign3.text=document.getString("Design4Des3")
                    }

                    if(document.getString("Design6").equals("нет")){
                        design6.visibility=View.GONE
                        design7.visibility=View.GONE
                    }
                    if(document.getString("Design5").equals("нет")){
                        design5.visibility=View.GONE
                        design6.visibility=View.GONE
                        design7.visibility=View.GONE
                    } else {
                        if (document.getString("Design7").equals("нет")){
                            design7.visibility=View.GONE
                        }
                        branch.add(document.getString("Design5").toString())
                        if(!document.getString("Design7").equals("нет") && !document.getString("Design6").equals("нет")){
                            branch.add(document.getString("Design6").toString())
                            branch.add(document.getString("Design7").toString())
                        } else if(!document.getString("Design6").equals("нет") && document.getString("Design7").equals("нет")){

                            branch.add(document.getString("Design6").toString())
                        }


                        //кнопка для просмотра второй трубы
                        design5.setOnClickListener {
                            //меняем стиль у кнопок
                            design5.setBackgroundResource(R.drawable.default_outlined_button_style)
                            design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                            //меняем цвет текста
                            design5.setTextColor(getResources().getColor(R.color.text_color))
                            design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                            //загружаем фото и описание
                            Glide.with(viewOfLayout)
                                .load(document.getString("Design4Photo")).fitCenter()
                                .into(photoPipe2)
                            descriptionDesign1.text = document.getString("Design5Des1")
                            descriptionDesign2.text = document.getString("Design5Des2")
                            descriptionDesign3.text = document.getString("Design5Des3")
                        }

                        //кнопка для просмотра третей трубы
                        design6.setOnClickListener {
                            //меняем стиль у кнопок
                            design6.setBackgroundResource(R.drawable.default_outlined_button_style)
                            design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design7.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                            //меняем цвет текста
                            design6.setTextColor(getResources().getColor(R.color.text_color))
                            design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design7.setTextColor(getResources().getColor(R.color.text_news_des_date_color))

                            //загружаем фото и описание
                            Glide.with(viewOfLayout)
                                .load(document.getString("Design6Photo")).fitCenter()
                                .into(photoPipe2)
                            descriptionDesign1.text = document.getString("Design6Des1")
                            descriptionDesign2.text = document.getString("Design6Des2")
                            descriptionDesign3.text = document.getString("Design6Des3")
                        }

                        //кнопка для просмотра второй трубы
                        design7.setOnClickListener {
                            //меняем стиль у кнопок
                            design7.setBackgroundResource(R.drawable.default_outlined_button_style)
                            design1.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design3.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design2.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design5.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design6.setBackgroundResource(R.drawable.default_outlined_button_not_selected)
                            design4.setBackgroundResource(R.drawable.default_outlined_button_not_selected)

                            //загружаем фото и описание
                            Glide.with(viewOfLayout)
                                .load(document.getString("Design7Photo")).fitCenter()
                                .into(photoPipe2)
                            descriptionDesign1.text = document.getString("Design7Des1")
                            descriptionDesign2.text = document.getString("Design7Des2")
                            descriptionDesign3.text = document.getString("Design7Des3")

                            //меняем цвет текста
                            design7.setTextColor(getResources().getColor(R.color.text_color))
                            design1.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design3.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design2.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design5.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design6.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                            design4.setTextColor(getResources().getColor(R.color.text_news_des_date_color))
                        }
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
        //кнопка для покупки товара
        val button_buy = viewOfLayout.findViewById<TextView>(R.id.button_buy)
        button_buy.setOnClickListener {
            showDialog(branch)
        }
        return viewOfLayout
    }
    companion object {
        //иницилизируем данные из бандла, если он не нулевой
        //проверим, есть ли нужные ключи
        //получаем данные
        @JvmStatic
        fun newInstance(mainCategory: String, docMain: String, docIdPipe: String, namePipe: String) =
            PipeBuyInfo1Fragment().apply {
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

    //метод вызова диалогового окна для выбора типа трубы из серии
    fun showDialog(branch: ArrayList<String>){
        //инцилизурем созданное окно для сброса пароля
        val dialogConstraintLayout = requireActivity().findViewById<ConstraintLayout>(R.id.dialog_Constraint_Layout)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.add_to_cart_dialog, dialogConstraintLayout)
        val closeBth = view.findViewById<TextView>(R.id.close_btn)
        val resetBth = view.findViewById<TextView>(R.id.reset_btn)
        val title = view.findViewById<TextView>(R.id.nameObPipe)
        title.text = namePipe
        var branchSelected:String = branch[0]
        //инцилизируем выпадающий список
        val spinner = view.findViewById<Spinner>(R.id.spinner_type)
        //устанавливаем адаптер с данными и разметкой для выпадающего списка
        var adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner, branch)
        adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                branchSelected= branch[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        val alertDialog = builder.create()
        //кнопка закрыть окно
        closeBth.setOnClickListener {
            alertDialog.dismiss()
        }
        //инцицилизируем sharedPreferences и viewModel для получения и сохранения данных
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val gson = Gson()
        model = ViewModelProvider(requireActivity())[CartModel::class.java]
        val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
        var flag =false
        //кнопка для покупки
        resetBth.setOnClickListener {
            if(branchSelected!=branch[0]) {
                //проверяем наличие в корзине
                for (i in itemCart) {
                    if (i == "Труба $branchSelected")
                        flag = true
                }
                //если нет, то добавляем товар, иначе сообщение
                if (!flag) {
                    itemCart.add("Труба $branchSelected")
                    model!!.setData(itemCart)
                    val json: String = gson.toJson(itemCart)
                    editor!!.putString("order", json)
                    editor!!.commit()
                } else
                    Toast.makeText(requireActivity(), resources.getString(R.string.msg_exist_goods), Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(requireActivity(), resources.getString(R.string.msg_not_chose), Toast.LENGTH_LONG).show()
        }
        if(alertDialog.window!=null){
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }
}