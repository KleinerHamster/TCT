package com.example.tct.model

import java.util.Date

//класс объекта новость
data class News (var Title: String? = null, var About: String? = null,
                var TheNews: String? = null, var DateNews: Date? = null,
                var Photo: String? = null)