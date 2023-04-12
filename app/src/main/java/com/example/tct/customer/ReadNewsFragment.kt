package com.example.tct.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tct.R
import com.squareup.picasso.Picasso

//ключи для сохранения настроек
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"


class ReadNewsFragment : Fragment() {
    private lateinit var viewOfLayout: View

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null


    //параметры view, которые будем менять -
    //инициализировать в конструкторе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
        }
    }

    //метод при создание и отображение активности
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout= inflater.inflate(R.layout.fragment_read_news, container, false)

        //устанавливаем полученные данные для отображения
        val titleNews = viewOfLayout.findViewById<TextView>(R.id.titleNews)
        titleNews.text = param1
        val descriptionNews = viewOfLayout.findViewById<TextView>(R.id.descriptionNews)
        descriptionNews.text = param2
        val readNews = viewOfLayout.findViewById<TextView>(R.id.readNews)
        readNews.text = param3
        val dateNews = viewOfLayout.findViewById<TextView>(R.id.dateNews)
        dateNews.text = param4
        val photoNews = viewOfLayout.findViewById<ImageView>(R.id.photoNews)

        val newsPhoto=param5

        //загружаем фотографию через библиотеку Glide
        Glide
            .with(viewOfLayout)
            .load(newsPhoto)
            .fitCenter()
            .placeholder(R.drawable.lg)
            .into(photoNews)

        //кнопка для возращения к списку новостей
        val backToListNews = viewOfLayout.findViewById<ImageView>(R.id.backToListNews)
        backToListNews.setOnClickListener{
            loadFragment(NewsCustomerFragment())
        }
        return viewOfLayout
    }

    companion object {
        //иницилизируем данные из бандла, если он не нулевой
        //проверим, есть ли нужные ключи
        //получаем данные
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: String, param5: String) =
            ReadNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                    putString(ARG_PARAM5, param5)
                }
            }
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_information,fragment)
        transaction?.commit()
    }
}