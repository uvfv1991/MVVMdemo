package com.example.agroapp.repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;


import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.Navigation;
import com.example.agroapp.repository.source.HttpDataSource;
import com.example.agroapp.repository.source.LocalDataSource;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * author : jiangxue
 * date : 2023/5/16 16:14
 * description :数据仓库
 */
public class AgroRepository extends BaseModel implements HttpDataSource {
    private volatile static AgroRepository INSTANCE = null;

    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private AgroRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static AgroRepository getInstance(HttpDataSource httpDataSource,
                                                LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (AgroRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AgroRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }



    @Override
    public Observable<BaseResponse<List<Banner>>> getBannerData() {
        return mHttpDataSource.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<Article>>> getTopArticleListData() {
        return mHttpDataSource.getTopArticleListData();
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getArticleListData(int pageNum) {
        return mHttpDataSource.getArticleListData(pageNum);
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getProjectArticleListData(int id, int pageNum) {
        return mHttpDataSource.getProjectArticleListData(id,pageNum);
    }

    @Override
    public Observable<BaseResponse<List<Navigation>>> getNavigationListData() {
        return mHttpDataSource.getNavigationListData();
    }

    @Override
    public Observable<Object> getDiscoverData(String url) {
        return mHttpDataSource.getDiscoverData(url);
    }

    @Override
    public Observable<Object> getNominateData(String url) {
        return mHttpDataSource.getNominateData(url);
    }

}
