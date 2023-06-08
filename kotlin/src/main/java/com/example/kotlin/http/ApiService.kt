package com.example.kotlin.http

import android.database.Observable
import com.example.kotlin.data.Article
import com.example.kotlin.data.ArticleList
import com.example.kotlin.data.Banner
import com.example.kotlin.data.Navigation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 *  author : jiangxue
 *  date : 2023/6/1 16:49
 *  description :网络的api
 */

open interface ApiService {
    /* -----------HomeData--------------*/
    /**
     * 获取首页Banner数据
     *
     * @return Banner数据
     */
    @GET("banner/json")
    fun getBannerData(): Observable<BaseResponse<List<Banner>>>

    /**
     * 获取首页置顶文章列表数据
     *
     * @return 置顶文章列表数据
     */
    @GET("article/top/json")
    fun getTopArticleListData(): Observable<BaseResponse<List<Article>>>

    /**
     * 获取首页文章列表数据
     *
     * @param pageNum 页数
     * @return 文章列表数据
     */
    @GET("article/list/{pageNum}/json")
    fun getArticleListData(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleList>>

    @GET("wxarticle/list/{id}/{pageNum}/json")
    fun getProjectArticleListData(@Path("id") id: Int, @Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleList>>

    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET("navi/json")
    fun getNavigationListData(): Observable<BaseResponse<List<Navigation>>>

    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET
    fun getDiscoverData(@Url path: String): Observable<Any>

    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET
    fun getNominateData(@Url path: String?): Observable<Any> //Any 相当于Object
}
