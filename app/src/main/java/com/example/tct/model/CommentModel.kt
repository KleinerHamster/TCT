package com.example.tct.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//ViewModel - здесь удобно держать все данные, которые нужны вам для формирования экрана.
// Они будут жить при поворотах экрана, но умрут, когда приложение будет убито системой.
class CommentModel : ViewModel() {
    //лист с данными
    //MutableLiveData является расширением LiveData, с отличием в том что это не абстрактный класс и
    // методы setValue(T) и postValue(T)
    //LiveData - хранилище данных, работающее по принципу паттерна Observer.
    // Это хранилище умеет делать две вещи:
    //1) В него можно поместить какой-либо объект
    //2) На него можно подписаться и получать объекты, которые в него помещают.
    val commentForApplying: MutableLiveData<String> = MutableLiveData()

    init {
        commentForApplying.value=""
    }
    fun get() = commentForApplying

    //метод для переустановки даных
    fun setData(commentFor: String) {
        commentForApplying.value = commentFor
    }

}