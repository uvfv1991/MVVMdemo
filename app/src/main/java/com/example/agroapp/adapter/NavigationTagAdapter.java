package com.example.agroapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.agroapp.R;
import com.example.agroapp.model.Article;
import com.zhy.view.flowlayout.FlowLayout;

import me.goldze.mvvmhabit.binding.viewadapter.base.BaseTagAdapter;

/**
 * 导航TagAdapter
 */
public class NavigationTagAdapter extends BaseTagAdapter<Article> {

    @Override
    public View getView(FlowLayout parent, int position, Article article) {
        TextView tvTag = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_navigation_tag, parent, false);
        tvTag.setText(article.getTitle());

        return tvTag;
    }
}
