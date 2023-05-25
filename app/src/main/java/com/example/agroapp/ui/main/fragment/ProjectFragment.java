package com.example.agroapp.ui.main.fragment;

import androidx.fragment.app.Fragment;

import com.example.agroapp.ui.main.base.BasePagerFragment;
import com.example.agroapp.ui.main.fragment.Fragment.CompleteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/22 13:30
 * description :
 */
public class ProjectFragment extends BasePagerFragment {
    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new CompleteFragment());
        list.add(new CompleteFragment());
        list.add(new CompleteFragment());
        list.add(new CompleteFragment());
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        //这里也可以用recyclerview替用
        List<String> list = new ArrayList<>();
        list.add("完整项目");
        list.add("跨平台应用");
        list.add("资源聚合类");
        list.add("动画");
        return list;
    }
}

