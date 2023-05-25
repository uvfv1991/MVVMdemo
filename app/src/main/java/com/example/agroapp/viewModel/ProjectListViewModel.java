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
 * date : 2023/5/16 16:14
 * description :获取首页数据
 */

public class ProjectListViewModel extends BaseViewModel<AgroRepository>{

    public final ObservableList<Object> dataList = new ObservableArrayList<>();



    private boolean mRefresh;

    private int mPageNum;

    private int mId;

    //封装一个界面发生改变的观察者(下拉刷新和向上划)
    public UIChangeObservable uc = new UIChangeObservable();

    public ProjectListViewModel(@NonNull Application application) {
        super(application);
        
    }

    public ObservableList<ProjectItemViewModel> observableList = new ObservableArrayList<ProjectItemViewModel>();
    public ItemBinding<ProjectItemViewModel> itemBinding = ItemBinding.of(com.example.agroapp.BR.viewModel, R.layout.item_project);

    private AgroRepository mrepository;

    public void setId(int id) {
        mId = id;
    }
    //加载数据
    public void loadData() {
        getAllData();
    }


    public void loadMoreData() {
        mPageNum++;
        getMoreArticleList();
    }

    private void getMoreArticleList() {
        model.getArticleListData(mPageNum)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(ProjectListViewModel.this) //请求与ViewModel周期同步
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
                            dataList.clear();
                            dataList.addAll(entity.getData().getDatas());
                            ProjectItemViewModel itemViewModel = new ProjectItemViewModel(ProjectListViewModel.this, article);
                            //itemViewModel.entity.get().getLink();
                            observableList.add(itemViewModel);
                        }
                        //刷新完成收回
                        uc.finishLoadmore.call();
                    }
                });

    };



    //获取数据
    private void getAllData() {

        mrepository.getProjectArticleListData(mId, 1)
                .compose(RxUtils.schedulersTransformer())
                .subscribeWith(new Observer<BaseResponse<ArticleList>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                       // addSubscribe(d);
                    }

                    @Override
                    public void onNext(BaseResponse<ArticleList> articleList) {


                        if (articleList != null && !Utils.isListEmpty(articleList.getData().getDatas())) {
                            dataList.clear();
                            dataList.addAll(articleList.getData().getDatas());
                            mPageNum = 1;
                            for (Article article : articleList.getData().getDatas()) {

                                ProjectItemViewModel itemViewModel = new ProjectItemViewModel(ProjectListViewModel.this, article);
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

    }



    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
        //条目被点击
        public SingleLiveEvent<String> itemclick = new SingleLiveEvent<String>();

        //删除选中的条目
        public SingleLiveEvent<ProjectItemViewModel> deleteitemclick = new SingleLiveEvent<ProjectItemViewModel>();
       /* //删除数据
        public SingleLiveEvent<ProjectItemViewModel> deleteItemLiveData = new SingleLiveEvent<ProjectItemViewModel>();*/
    }

    public ProjectListViewModel(@NonNull Application application, AgroRepository repository) {
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

    /**
     * 删除条目
     *
     * @param ProjectItemViewModel
     */
    public void deleteItem(ProjectItemViewModel projectItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(projectItemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param ProjectItemViewModel
     * @return
     */
    public int getItemPosition(ProjectItemViewModel viewModel) {
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




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
