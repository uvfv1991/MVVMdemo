package com.example.agroapp.base;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * PagerAdapter的基类
 */
public abstract class BasePagerAdapter<T> extends FragmentPagerAdapter {

    protected SparseArray<Fragment> mFragmentMap = new SparseArray<>();

    protected List<T> mDataList;

    public BasePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    /**
     * 设置数据列表
     *
     * @param dataList 数据列表
     */
    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 释放缓存的Fragment
     */
    public void release() {
        mFragmentMap.clear();
    }
}
