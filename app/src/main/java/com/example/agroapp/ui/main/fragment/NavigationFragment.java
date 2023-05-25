package com.example.agroapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroapp.R;
import com.example.agroapp.adapter.FlowTagAdapter;
import com.example.agroapp.adapter.ImageAdapter;
import com.example.agroapp.app.AppViewModelFactory;
import com.example.agroapp.databinding.FragmentNavigationBinding;
import com.example.agroapp.model.Banner;
import com.example.agroapp.ui.main.base.BasePagerFragment;
import com.example.agroapp.ui.main.fragment.Fragment.CompleteFragment;
import com.example.agroapp.viewModel.HomeViewModel;
import com.example.agroapp.viewModel.NavigationViewModel;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * author : jiangxue
 * date : 2023/5/22 13:30
 * description :
 */
public class NavigationFragment extends BaseFragment<FragmentNavigationBinding, NavigationViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_navigation;
    }

    @Override
    public int initVariableId() {
        return com.example.agroapp.BR.viewModel;
    }





    @Override
    public NavigationViewModel initViewModel() {

        // Get the ViewModel
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        return ViewModelProviders.of(this, factory).get(NavigationViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initRecyclerView();
        viewModel.loadData();


    }



    @Override
    public void initViewObservable() {

    }

    private void initRecyclerView() {

    }


}

