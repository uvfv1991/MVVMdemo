package me.goldze.mvvmhabit.binding.viewadapter.verticaltab;


import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import me.goldze.mvvmhabit.binding.viewadapter.base.BasePagerAdapter;
import me.goldze.mvvmhabit.binding.viewadapter.base.BaseTagAdapter;
import me.goldze.mvvmhabit.utils.Utils;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * 应用的BindingAdapter
 */
public final class WanBindingAdapter {

   /* private WanBindingAdapter() {

    }

    /**
     * 设置ViewPager的数据列表
     *
     * @param viewPager ViewPager
     * @param dataList  数据列表
     * @param <T>       数据类型
     */
   /* @BindingAdapter("app:dataList")
    public static <T> void setDataList(ViewPager viewPager, List<T> dataList) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof BasePagerAdapter) {
            BasePagerAdapter basePagerAdapter = (BasePagerAdapter) adapter;
            basePagerAdapter.setDataList(dataList);
        }
    }*/

    /**
     * 设置TagFlowLayout的数据列表
     *
     * @param tagFlowLayout TagFlowLayout
     * @param dataList      数据列表
     * @param <T>           数据类型
     */
   /* @BindingAdapter("app:dataList")
    public static <T> void setDataList(TagFlowLayout tagFlowLayout, List<T> dataList) {
        TagAdapter adapter = tagFlowLayout.getAdapter();
        if (adapter instanceof BaseTagAdapter) {
            BaseTagAdapter baseTagAdapter = (BaseTagAdapter) adapter;
            baseTagAdapter.setDataList(dataList);
        }
    }*/



    /**
     * 设置VerticalTabLayout的标题
     *
     * @param tabLayout VerticalTabLayout
     * @param titleList 标题列表
     */
    /*@BindingAdapter("app:titleList")
    public static void setTitleList(VerticalTabLayout tabLayout, List<String> titleList) {
        if (Utils.isListEmpty(titleList)) {
            return;
        }

        tabLayout.setTabAdapter(new SimpleTabAdapter() {

            @Override
            public int getCount() {
                return titleList.size();
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(titleList.get(position))
                        .setTextSize(16)
                        .build();
            }
        });
    }*/
}
