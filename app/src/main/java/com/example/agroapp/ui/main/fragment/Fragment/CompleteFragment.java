package com.example.agroapp.ui.main.fragment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.agroapp.R;
import com.example.agroapp.app.AppViewModelFactory;
import com.example.agroapp.databinding.FragmentHomepageBinding;
import com.example.agroapp.ui.main.activity.DetailsActivity;
import com.example.agroapp.viewModel.HomeViewModel;
import com.example.agroapp.viewModel.ProjectItemViewModel;
import com.example.agroapp.viewModel.ProjectListViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * author : jiangxue
 * date : 2023/5/22 13:38
 * description :完整项目
 */
public class CompleteFragment  extends BaseFragment<FragmentHomepageBinding, ProjectListViewModel>  {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_project_list;
    }

    @Override
    public int initVariableId() {
        return com.example.agroapp.BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setId(402);
        viewModel.loadData();
    }

    @Override
    public ProjectListViewModel initViewModel() {

        // Get the ViewModel
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        return ViewModelProviders.of(this, factory).get(ProjectListViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.itemclick.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",s);
                intent.putExtra("data",bundle);
                startActivity(intent);
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

        //监听删除条目
        viewModel.uc.deleteitemclick.observe(this, new Observer<ProjectItemViewModel>() {
            @Override
            public void onChanged(@Nullable final ProjectItemViewModel model) {
                int index = viewModel.getItemPosition(model);
                //删除选择对话框
                MaterialDialogUtils.showBasicDialog(getContext(), "提示", "是否删除【" + model.entity.get().getChapterName() + "】？ position：" + index)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtils.showShort("取消");
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.deleteItem(model);
                    }
                }).show();
            }
        });

    }
}