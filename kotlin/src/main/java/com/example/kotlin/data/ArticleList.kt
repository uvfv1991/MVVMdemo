package com.example.kotlin.data

/**
 *  author : jiangxue
 *  date : 2023/6/2 9:07
 *  description :
 */

data class ArticleList (
        var curPage: Int,
        var offset: Int,
        var isOver:  Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int,
        var datas: List<Article>?
)



