package com.example.kotlin.data

/**
 *  author : jiangxue
 *  date : 2023/6/2 9:07
 *  description :
 */
class Navigation {
    private var cid: Int? = null
    private var name: String? = null
    private var articles: List<Article>? = null
    fun getCid(): Int? {
        return cid
    }

    fun setCid(cid: Int?) {
        this.cid = cid
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getArticles(): List<Article>? {
        return articles
    }

    fun setArticles(articles: List<Article>?) {
        this.articles = articles
    }
}