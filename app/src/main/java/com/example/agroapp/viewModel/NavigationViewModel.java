package com.example.agroapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.agroapp.R;
import com.example.agroapp.base.BaseItemViewModel;
import com.example.agroapp.http.HttpCode;
import com.example.agroapp.model.Article;
import com.example.agroapp.model.ArticleList;
import com.example.agroapp.model.Banner;
import com.example.agroapp.model.BannerData;
import com.example.agroapp.model.HomepageData;
import com.example.agroapp.model.Navigation;
import com.example.agroapp.navigator.DetailsNavigator;
import com.example.agroapp.repository.AgroRepository;

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
 * date : 2023/5/22 16:14
 * description :获取导航数据
 */

public class NavigationViewModel extends BaseViewModel<AgroRepository> {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableList<Object> dataList = new ObservableArrayList<Object>();


    public final ObservableList<String> titleList = new ObservableArrayList<>();


    private int mPageNum;

    //封装一个界面发生改变的观察者(下拉刷新和向上划)
    public UIChangeObservable uc = new UIChangeObservable();

    public NavigationViewModel(@NonNull Application application) {
        super(application);

    }


    //给RecyclerView添加ObservableList
    public ObservableList<NavigationItemViewModel> observableList = new ObservableArrayList<NavigationItemViewModel>();
    //给RecyclerView添加ItemBinding-文章列表
    public ItemBinding<NavigationItemViewModel> itemBinding = ItemBinding.of(com.example.agroapp.BR.viewModel, R.layout.item_navigation);

    private AgroRepository mrepository;


    //加载数据
    public void loadData() {
        getNavigationData();
    }


    public void loadMoreData() {
        mPageNum++;
        getMoreArticleList();
    }

    private void getMoreArticleList() {
        model.getArticleListData(mPageNum)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(NavigationViewModel.this) //请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ToastUtils.showShort("上拉加载");
                    }
                })
                .subscribe(new Consumer<BaseResponse<ArticleList>>() {
                    @Override
                    public void accept(BaseResponse<ArticleList> entity) throws Exception {

                        //刷新完成收回
                        uc.finishLoadmore.call();
                    }
                });

    }


    //获取weather数据
    public void getNavigationData() {
        model.getNavigationListData()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(NavigationViewModel.this) //请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ToastUtils.showShort("上拉加载");
                    }
                })
                .subscribe(new Observer<BaseResponse<List<Navigation>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<Navigation>> navigationList) {
                        List<Navigation> navigations = navigationList.getData();
                        dataList.clear();
                        if (!Utils.isListEmpty(navigations)) {
                            for (Navigation navigation : navigations) {
                                titleList.add(navigation.getName());

                            }
                            dataList.addAll(navigations);

                        }
                        for (Navigation navigation : navigations) {
                           /* NavigationItemViewModel itemViewModel = new NavigationItemViewModel(NavigationViewModel.this, navigation);
                            observableList.add(itemViewModel);*/
                        }
                        //刷新完成收回
                        uc.finishLoadmore.call();
                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }

    public NavigationViewModel(@NonNull Application application, AgroRepository repository) {
        super(application, repository);
        mrepository = repository;
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





    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


