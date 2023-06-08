package com.example.kotlin.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.app.AppApplication
import com.example.kotlin.base.AppRepository
import com.example.kotlin.data.Article
import com.example.kotlin.data.ArticleList
import com.example.kotlin.data.Banner
import kotlinx.coroutines.launch
import java.util.*

/**
 *  author : jiangxue
 *  date : 2023/6/1 16:22
 *  description :用于持有和UI元素相关的数据，以保证这些数据在屏幕旋转时不会丢失，以及负责和仓库之间进行通讯。
 */

class HomeViewModel(private val repository: AppRepository) : ViewModel() {
    var bannerlivedata = MutableLiveData<List<Banner>>()
    val arcticledata = MutableLiveData<Article>()
    val bannerChanged = MutableLiveData<Int>()
    var dataChanged = MutableLiveData<Int>()
    var isLoading = MutableLiveData<Boolean>()

    //MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
    var refreshing = MutableLiveData<Boolean>()
    private val mRefresh = false
    private var mPageNum = 0

    var dataInitialized = MutableLiveData<Boolean>()
    val dataList = ArrayList<Article>()
    //封装一个界面发生改变的观察者(下拉刷新和向上划)
    //var uc = UIChangeObservable()


    //加载数据
    fun loadData() {
        getHomeData()
    }

    fun loadMoreData() {
        mPageNum++
        getMoreArticleList()
    }
    //MutableList 表示可变列表 元素类型不可变，但是大小可变
    lateinit var article: ArticleList
    var banner= ArrayList<Banner>()
    fun getMoreArticleList() {

        //obj?.let { }(相当于java的非空判断，当obj不为空时，才执行大括号内的代码段)
        launch {
            article= repository.getArticleListData(mPageNum)!!
            dataList.addAll(article.datas!! )
            dataInitialized.value = true

        }

    }

    //获取weather数据
    @SuppressLint("CheckResult")
    fun getHomeData() {
        dataList.clear()
        banner.clear()
        mPageNum = 0
        //banner
        launchBanner {
            banner= repository.requestBannerData()

        }
        launch {
            article= repository.getArticleListData(mPageNum)!!
            dataList.addAll(article.datas!! )
            dataInitialized.value = true

        }

/*

        *//* 这个界面需要用的3个接口，必须它们都进行请求后才可以进行展示，所以用了zip操作符；*//*
        //正在刷新中不会再响应刷新操作
        val bannerDataObservable: Observable<BaseResponse<List<Banner>>> = model!!.getBannerData()
        val topArticleDataObservable: Observable<BaseResponse<List<Article>>> = model!!.getTopArticleListData()
        val articleListObservable: Observable<BaseResponse<ArticleList>> = model!!.getArticleListData(mPageNum)


        val zipped = Observables.zip(bannerDataObservable, topArticleDataObservable, articleListObservable) { bannerDataObservable: BaseResponse<List<Banner>>, topArticleDataObservable: BaseResponse<List<Article>>, articleListObservable: BaseResponse<ArticleList> ->
        }
        zipped?.subscribeBy(object : Flow.Subscriber<BaseResponse<HomepageData>>, (Throwable) -> Unit {
            override fun onNext(t: BaseResponse<HomepageData>?) {
                val bannerData: BannerData? = t?.getData()?.getBannerData()
                val articleList: ArticleList? = t?.getData()?.getArticleList()
                dataList.clear()
                bannerlist.clear()
                mPageNum = 0
                MutableLiveData.clear()
                if (articleList != null && !Utils.isListEmpty(articleList.datas)) {
                    dataList.addAll(articleList.datas!!)
                    if (bannerData != null && !Utils.isListEmpty(bannerData.bannerList)) {
                        bannerlist.addAll(bannerData.bannerList!!)

                        //在这里绑定BannerItem

                    }

                    //绑定文章列表
                    for (article in dataList) {
                        val itemViewModel = HomeItemViewModel(this@HomeViewModel, article)
                        //双向绑定动态添加Item
                        MutableLiveData.add(itemViewModel)
                    }
                } else {
                }
            }

            override fun onComplete() {
                //关闭对话框
                dismissDialog()
                //请求刷新完成收回
                uc.finishRefreshing.call()
            }

            override fun onSubscribe(s: Subscription?) {
                TODO("Not yet implemented")
            }

            override fun onError(t: Throwable?) {
                //关闭对话框
                dismissDialog()
                //请求刷新完成收回
                uc.finishRefreshing.call()
                if (t is ResponseThrowable) {
                    //ToastUtils.showShort(e.message?)
                }
            }

            override fun invoke(p1: Throwable) {
                TODO("Not yet implemented")
            }

        })*/
    }


    //协程依赖于线程，并且内部实现了相关异步操作。
    /*1.viewModel 销毁时自动地取消子协程。
    * 主要处理 没法在主线程完成的繁重操作，比如UI更新等*/
    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            dataList.clear()
            block()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(AppApplication.context, t.message, Toast.LENGTH_SHORT).show()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        }
    }

    private fun launchBanner(block: suspend () -> Unit) = viewModelScope.launch {
        try {

            banner.clear()
            block()
            bannerChanged.value = bannerChanged.value?.plus(1)

        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(AppApplication.context, t.message, Toast.LENGTH_SHORT).show()
            bannerChanged.value = bannerChanged.value?.plus(1)
        }
    }


}
