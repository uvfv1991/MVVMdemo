package com.example.kotlin.http.api

import com.example.kotlin.data.ArticleList
import com.example.kotlin.data.Banner
import com.example.kotlin.http.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {


    /**
     * 获取首页文章列表数据
     *
     * @param pageNum 页数
     * @return 文章列表数据
     */
    @GET("article/list/{pageNum}/json")
    fun getArticleListData(@Path("pageNum") pageNum: Int): Call<BaseResponse<ArticleList>>

    /**
     * 获取首页Banner数据
     *
     * @return Banner数据
     */
    @GET("banner/json")
    fun getBannerData(): Call<BaseResponse<ArrayList<Banner>>>
   /* @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String): Call<HeWeather>

    @GET("api/bing_pic")
    fun getBingPic(): Call<String>*/

}