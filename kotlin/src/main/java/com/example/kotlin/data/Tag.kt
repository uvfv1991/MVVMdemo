package com.example.kotlin.data

/**
 *  author : jiangxue
 *  date : 2023/6/2 9:13
 *  description :
 */
class Tag {
    private var name: String? = null
    private var url: String? = null
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }
}
