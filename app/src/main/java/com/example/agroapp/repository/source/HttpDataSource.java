package com.example.agroapp.repository.source;


import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.Navigation;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by jx
 */
public interface HttpDataSource {

    /* -----------HomeData--------------*/
    /**
     * 获取首页Banner数据
     */

    Observable<BaseResponse<List<Banner>>> getBannerData();

    /**
     * 获取首页置顶文章列表数据
     */

    Observable<BaseResponse<List<Article>>> getTopArticleListData();

    /**
     * 获取首页文章列表数据
     */

    Observable<BaseResponse<ArticleList>> getArticleListData(int pageNum);

    /**
     *  获取某个项目分类文章列表数据
     */

    Observable<BaseResponse<ArticleList>> getProjectArticleListData(int id,int pageNum);

    /**
     * 获取导航列表数据
     *
     * @return 导航列表数据
     */

    Observable<BaseResponse<List<Navigation>>> getNavigationListData();


    /**
     * 获取发现列表数据
     *
     * @return 发现列表数据
     * @param url
     */

    Observable<Object> getDiscoverData(String url);


    /**
     * 获取发现列表数据
     *
     * @return 推荐列表数据
     * @param url
     */

    Observable<Object> getNominateData(String url);

}
