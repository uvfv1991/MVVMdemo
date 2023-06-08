package com.example.kotlin.data

/**
 *  author : jiangxue
 *  date : 2023/6/1 16:58
 *  description :
 */

data class Banner(
    var desc: String,
     var id: Int,
     var imagePath: String,
     var isVisible: Int,
     var order: Int,
     var title: String,
    var type: Int,
     var url: String
)