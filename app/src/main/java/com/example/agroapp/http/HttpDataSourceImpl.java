package com.example.agroapp.http;


import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.Navigation;
import com.example.agroapp.repository.source.HttpDataSource;
import com.example.agroapp.http.service.DemoApiService;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by jx
 *
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }



    @Override
    public Observable<BaseResponse<List<Banner>>> getBannerData() {
        return apiService.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<Article>>> getTopArticleListData() {
        return apiService.getTopArticleListData();
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getArticleListData(int pageNum) {
        return apiService.getArticleListData(pageNum);
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getProjectArticleListData(int id, int pageNum) {
        return apiService.getProjectArticleListData(id,pageNum);
    }

    @Override
    public Observable<BaseResponse<List<Navigation>>> getNavigationListData() {
        return apiService.getNavigationListData();
    }

    @Override
    public Observable<Object> getDiscoverData(String url) {
        return apiService.getDiscoverData(url);
    }

    @Override
    public Observable<Object> getNominateData(String url) {
        return apiService.getNominateData(url);
    }
}
