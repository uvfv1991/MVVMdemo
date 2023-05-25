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
import com.example.agroapp.model.ModelBanner;
import com.example.agroapp.model.ModelCategoryCart;
import com.example.agroapp.model.ModelSubjectCard;
import com.example.agroapp.model.ModelTitleView;
import com.example.agroapp.model.ModelTopBanner;
import com.example.agroapp.repository.AgroRepository;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;
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
 * description :获取发现的数据
 */

public class DiscoverViewModel extends BaseViewModel<AgroRepository> {

    public MutableLiveData<ArrayList<Object>> livedata = new MutableLiveData<ArrayList<Object>>();
    public final ArrayList<Object> dataList = new ArrayList<Object>();

    String base="http://baobab.kaiyanapp.com/api/";

    String url=base+"v7/index/tab/discovery?udid=fa53872206ed42e3857755c2756ab683fc22d64a&vc=591&vn=6.2.1&size=720X1280&deviceModel=Che1-CL20&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=19";

    //封装一个界面发生改变的观察者(下拉刷新和向上划)
    public UIChangeObservable uc = new UIChangeObservable();

    public DiscoverViewModel(@NonNull Application application) {
        super(application);

    }




    private AgroRepository mrepository;


    //加载数据
    public void loadData() {
       getDiscoverData();
    }




    private void getDiscoverData() {
        model.getDiscoverData(url)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ToastUtils.showShort("上拉加载");
                    }
                })
                .subscribe(new DisposableObserver<Object>() {


                    @Override
                    public void onNext(Object o) {
                        Gson gson = new Gson();
                        String result = gson.toJson(o);
                        dataList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray itemList = jsonObject.getJSONArray("itemList");

                            for (int i=0;i<itemList.length();i++){
                                JSONObject data =itemList.getJSONObject(i);
                                String type = data.getString("type");
                                switch (type){
                                    case "horizontalScrollCard"://水平滚动
                                        ModelTopBanner modelTopBanner =gson.fromJson(itemList.get(i).toString(), ModelTopBanner.class);
                                        dataList.add(modelTopBanner);

                                        break;

                                    case "specialSquareCardCollection"://水平滚动
                                        ModelCategoryCart model =gson.fromJson(itemList.get(i).toString(), ModelCategoryCart.class);
                                        dataList.add(model);

                                        break;

                                    case "columnCardList"://水平滚动
                                        ModelSubjectCard cardmodel =gson.fromJson(itemList.get(i).toString(), ModelSubjectCard.class);
                                        dataList.add(cardmodel);

                                        break;

                                    case "textCard"://水平滚动
                                        ModelTitleView titlemodel =gson.fromJson(itemList.get(i).toString(), ModelTitleView.class);
                                        dataList.add(titlemodel);

                                        break;

                                    case "banner":
                                        ModelBanner banner =gson.fromJson(itemList.get(i).toString(), ModelBanner.class);
                                        dataList.add(banner);

                                        break;

                                }
                            }

                            livedata.postValue(dataList);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

                        ToastUtils.showShort("加载完成");
                    }
                });

    };




    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }

    public DiscoverViewModel(@NonNull Application application, AgroRepository repository) {
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

            //loadMoreData();
        }

    });





    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
