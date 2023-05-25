package com.example.agroapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.agroapp.R;
import com.example.agroapp.http.HttpCode;
import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.BannerData;
import com.example.agroapp.model.HomepageData;
import com.example.agroapp.repository.AgroRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * author : jiangxue
 * date : 2023/5/16 16:14
 * description :获取首页数据
 */

public class HomeViewModel extends BaseViewModel<AgroRepository> {

    public MutableLiveData<List<Banner>> bannerlivedata = new MutableLiveData<List<Banner>>();

    public final ObservableList<Article> dataList = new ObservableArrayList<>();
    public final ObservableList<Banner> bannerlist = new ObservableArrayList<>();

    //MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
    public SingleLiveEvent<Boolean> refreshing = new SingleLiveEvent<Boolean>();

    private boolean mRefresh;
    private int mPageNum;

    //封装一个界面发生改变的观察者(下拉刷新和向上划)
    public UIChangeObservable uc = new UIChangeObservable();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        initBanner();
    }

    private void initBanner() {


    }

    //给RecyclerView添加ObservableList
    public ObservableList<HomeItemViewModel> observableList = new ObservableArrayList<HomeItemViewModel>();
    //给RecyclerView添加ItemBinding-文章列表
    public ItemBinding<HomeItemViewModel> itemBinding = ItemBinding.of(com.example.agroapp.BR.viewModel, R.layout.item_article);

    private AgroRepository mrepository;


    //加载数据
    public void loadData() {
        getHomeData();
    }


    public void loadMoreData() {
        mPageNum++;
        getMoreArticleList();
    }

    private void getMoreArticleList() {
        model.getArticleListData(mPageNum)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(HomeViewModel.this) //请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ToastUtils.showShort("上拉加载");
                    }
                })
                .subscribe(new Consumer<BaseResponse<ArticleList>>() {
                    @Override
                    public void accept(BaseResponse<ArticleList> entity) throws Exception {
                        for (Article article : entity.getData().getDatas()) {
                            HomeItemViewModel itemViewModel = new HomeItemViewModel(HomeViewModel.this, article);
                            observableList.add(itemViewModel);
                        }
                        //刷新完成收回
                        uc.finishLoadmore.call();
                    }
                });

    };



    //获取weather数据
    public void getHomeData() {

       /* 这个界面需要用的3个接口，必须它们都进行请求后才可以进行展示，所以用了zip操作符；*/
        //正在刷新中不会再响应刷新操作
        Observable<BaseResponse<List<Banner>>> bannerDataObservable = model.getBannerData();
        Observable<BaseResponse<List<Article>>> topArticleDataObservable = model.getTopArticleListData();
        Observable<BaseResponse<ArticleList>> articleListObservable = model.getArticleListData(mPageNum);
        final Disposable[] mdisposable = {null};
        Observable.zip(bannerDataObservable, topArticleDataObservable, articleListObservable,
                new Function3<BaseResponse<List<Banner>>, BaseResponse<List<Article>>, BaseResponse<ArticleList>, BaseResponse<HomepageData>>() {
                    @Override
                    public BaseResponse<HomepageData> apply(BaseResponse<List<Banner>> bannerResponse,
                                                            BaseResponse<List<Article>> topListResponse,
                                                            BaseResponse<ArticleList> articleListResponse) throws Exception {
                        BaseResponse<HomepageData> response = new BaseResponse<>();
                        if (articleListResponse.getErrorCode()!= HttpCode.SUCCESS) {
                            response.setErrorMsg(articleListResponse.getErrorMsg());
                            return response;
                        }

                        HomepageData homepageData = new HomepageData();
                        homepageData.setArticleList(articleListResponse.getData());

                        if (bannerResponse.getErrorCode()  == HttpCode.SUCCESS) {
                            bannerlivedata.postValue(bannerResponse.getData());

                        }

                        if (topListResponse.getErrorCode() == HttpCode.SUCCESS) {
                            ArticleList articleList = articleListResponse.getData();
                            List<Article> topList = topListResponse.getData();

                            if (articleList != null && articleList.getDatas() != null && !Utils.isListEmpty(topList)) {
                                for (Article article : topList) {
                                    article.setTop(true);
                                }

                                articleList.getDatas().addAll(0, topList);
                            }
                        }

                        response.setErrorCode(HttpCode.SUCCESS);
                        response.setData(homepageData);

                        return response;

                    }
                }).compose(RxUtils.schedulersTransformer())
                .subscribeWith(new Observer<BaseResponse<HomepageData>>() {


                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<HomepageData> homepageData) {
                        BannerData bannerData = homepageData.getData().getBannerData();
                        ArticleList articleList = homepageData.getData().getArticleList();
                        dataList.clear();
                        bannerlist.clear();
                        mPageNum = 0;
                        observableList.clear();


                        if (articleList != null && !Utils.isListEmpty(articleList.getDatas())) {
                            dataList.addAll(articleList.getDatas());
                            if (bannerData != null && !Utils.isListEmpty(bannerData.getBannerList())) {
                                bannerlist.addAll(bannerData.getBannerList());

                                //在这里绑定BannerItem

                             /*   for (Banner banner : bannerData.getBannerList()) {
                                    HomeItemViewModel itemViewModel = new HomeItemViewModel(HomeViewModel.this, article);
                                    //双向绑定动态添加Item
                                    observableList.add(itemViewModel);
                                }*/
                            }

                            //绑定文章列表
                            for (Article article : dataList) {
                                HomeItemViewModel itemViewModel = new HomeItemViewModel(HomeViewModel.this, article);
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }


                        } else {
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.call();
                        if (e instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) e).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.call();
                    }
                });
        //addSubscribe(mdisposable[0]);

    }
    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }

    public HomeViewModel(@NonNull Application application, AgroRepository repository) {
        super(application, repository);
        mrepository=repository;
    }


    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
            loadData();
        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("加载下一页数据");
            mPageNum++;
            loadMoreData();
        }

    });

  /*
     * 删除条目
     *
     * @param WeatherItemViewModel
     *//**//**//**//*
    public void deleteItem(WeatherItemViewModel WeatherItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(WeatherItemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param WeatherItemViewModel
     * @return
     */
    public int getItemPosition(HomeItemViewModel viewModel) {
        return observableList.indexOf(viewModel);
    }


    /**
     * 获取Banner条目下标
     *
     * @param
     * @return
     */
    public int getBannerItemPosition(BannerItemViewModel viewModel) {
        return observableList.indexOf(viewModel);
    }
    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到activity中处理,打开drawerble

        }
    });



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
