package com.example.tct.model

import java.util.*
import kotlin.collections.ArrayList

data class CustomerOrder(
    var Comment: String? = null,
    var DateOrder: Date? = null,
    var Goods: ArrayList<String> ?=null
)
