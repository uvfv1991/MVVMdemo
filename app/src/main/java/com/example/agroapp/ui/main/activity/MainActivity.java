package com.example.agroapp.ui.main.activity;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.agroapp.BR;
import com.example.agroapp.R;
import com.example.agroapp.app.AppViewModelFactory;
import com.example.agroapp.databinding.ActivityMainBinding;
import com.example.agroapp.ui.main.fragment.DiscoverFragment;
import com.example.agroapp.ui.main.fragment.HomepageFragment;
import com.example.agroapp.ui.main.fragment.NavigationFragment;
import com.example.agroapp.ui.main.fragment.ProjectFragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * author : jiangxue
 * date : 2023/5/16 16:06
 * description :首页
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {
    private List<Fragment> mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        super.initParam();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    public void initData() {
        //初始化Fragment
        initFragment();
        //initToolbar();
        //初始化底部Button
        initBottomTab();
    }



    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomepageFragment());
        mFragments.add(new DiscoverFragment());
        mFragments.add(new HomepageFragment());
        mFragments.add(new NavigationFragment());
        mFragments.add(new ProjectFragment());

        //默认选中第一个
        commitAllowingStateLoss(0);
    }
    private void initToolbar() {

        //setSupportActionBar(binding.iToolbar.tbToolbar);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle(R.string.app_name);
        actionBar.setTitle("测试APP");
    }
    private void initBottomTab() {
        NavigationController navigationController = binding.pagerBottomTab.material()
                .addItem(R.drawable.ic_homepage, "首页")
                .addItem(R.drawable.ic_system, "体系")
                .addItem(R.drawable.ic_we_chat, "公众号")
                .addItem(R.drawable.ic_navigation, "导航")
                .addItem(R.drawable.ic_project, "项目")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                commitAllowingStateLoss(index);
               // binding.iToolbar.tbToolbar.setTitle();
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    private void commitAllowingStateLoss(int position) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(position + "");
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = mFragments.get(position);
            transaction.add(R.id.frameLayout, currentFragment, position + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

}
