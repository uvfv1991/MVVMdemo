package com.example.kotlin.data

/**
 *  author : jiangxue
 *  date : 2023/6/2 10:23
 *  description :
 */
class HomepageData {
    private var bannerData: BannerData? = null
    private var articleList: ArticleList? = null
    fun getBannerData(): BannerData? {
        return bannerData
    }

    fun setBannerData(bannerData: BannerData?) {
        this.bannerData = bannerData
    }

    fun getArticleList(): ArticleList? {
        return articleList
    }

    fun setArticleList(articleList: ArticleList?) {
        this.articleList = articleList
    }
}