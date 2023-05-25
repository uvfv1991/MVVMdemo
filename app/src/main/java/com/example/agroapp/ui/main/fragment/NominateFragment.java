package com.example.agroapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.agroapp.R;
import com.example.agroapp.adapter.DiscoverAdapter;
import com.example.agroapp.adapter.NominateAdapter;
import com.example.agroapp.app.AppViewModelFactory;
import com.example.agroapp.databinding.FragmentDiscoverBinding;
import com.example.agroapp.viewModel.DiscoverViewModel;
import com.example.agroapp.viewModel.NominateViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * author : jiangxue
 * date : 2023/5/25 16:14
 * description :推荐
 */
public class NominateFragment extends BaseFragment<FragmentDiscoverBinding, NominateViewModel>  {

    private NominateAdapter myAdapter;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_nominate;
    }

    @Override
    public int initVariableId() {
        return com.example.agroapp.BR.viewModel;
    }





    @Override
    public NominateViewModel initViewModel() {

        // Get the ViewModel
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        return ViewModelProviders.of(this, factory).get(NominateViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.loadData();


    }




    @Override
    public void initViewObservable() {
        viewModel.livedata.observe(this, new Observer<Object>() {

            @Override
            public void onChanged(Object o) {
                ToastUtils.showShort("已经获取到数据啦啦啦啦");
                binding.rvRecyclerView.setAdapter(new DiscoverAdapter(getContext(),viewModel.livedata.getValue()));
            }
        });

        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.trDiscover.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.trDiscover.finishLoadmore();
            }
        });

    }

    private void initRecyclerView() {



    }

}
