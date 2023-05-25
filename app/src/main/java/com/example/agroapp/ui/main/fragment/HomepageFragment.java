package com.example.agroapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.agroapp.R;
import com.example.agroapp.adapter.ImageAdapter;
import com.example.agroapp.app.AppViewModelFactory;
import com.example.agroapp.databinding.FragmentHomepageBinding;
import com.example.agroapp.model.Banner;
import com.example.agroapp.viewModel.HomeViewModel;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * author : jiangxue
 * date : 2023/5/16 16:14
 * description :首页
 */
public class HomepageFragment extends BaseFragment<FragmentHomepageBinding, HomeViewModel>  {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_homepage;
    }

    @Override
    public int initVariableId() {
        return com.example.agroapp.BR.viewModel;
    }





    @Override
    public HomeViewModel initViewModel() {

        // Get the ViewModel
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initRecyclerView();
        initBanner();
        viewModel.loadData();


    }

    private void initBanner() {
        binding.bBanner.setIndicator(new CircleIndicator(getActivity()));
        binding.bBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        binding.bBanner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));
    }


    @Override
    public void initViewObservable() {
        //监听banner的数据变化

        viewModel.bannerlivedata.observe(this, new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> banners) {
                binding.bBanner.setAdapter(new ImageAdapter(viewModel.bannerlivedata.getValue()));
            }
        });

        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
    }

    private void initRecyclerView() {
       /*binding.rvRecyclerView.setAdapter(new ArticleListAdapter(viewModel.dataList));
        binding.rvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/
    }

}
