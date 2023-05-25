package com.example.agroapp.http.service;

import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.Navigation;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by jx
 */

public interface DemoApiService {
   /* -----------HomeData--------------*/
    /**
     * 获取首页Banner数据
     *
     * @return Banner数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<Banner>>> getBannerData();

    /**
     * 获取首页置顶文章列表数据
     *
     * @return 置顶文章列表数据
     */
    @GET("article/top/json")
    Observable<BaseResponse<List<Article>>> getTopArticleListData();

    /**
     * 获取首页文章列表数据
     *
     * @param pageNum 页数
     * @return 文章列表数据
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleList>> getArticleListData(@Path("pageNum") int pageNum);

    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<ArticleList>> getProjectArticleListData(@Path("id") int id, @Path("pageNum") int pageNum);


    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET("navi/json")
    Observable<BaseResponse<List<Navigation>>> getNavigationListData();


    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET
    Observable<Object> getDiscoverData(@Url String path);


    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */
    @GET
    Observable<Object> getNominateData(@Url String path);
}
