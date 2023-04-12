package com.example.tct.model

//класс объекта подкатегории
data class SubCategory (var Title: String? = null,
                        var order: String? = null,
                        var photo: String? = null,
                        val docId: String? = null,
                        val TypeOfFragment: String? = null)