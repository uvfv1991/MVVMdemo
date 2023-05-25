package com.example.agroapp.viewholder;

import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import com.example.agroapp.R;
import com.example.agroapp.adapter.NavigationTagAdapter;
import com.example.agroapp.base.BaseViewHolder;
import com.example.agroapp.databinding.ItemNavigationBinding;
import com.example.agroapp.navigator.DetailsNavigator;
import com.example.agroapp.viewModel.NavigationItemViewModel;
import com.example.agroapp.viewModel.NavigationViewModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * 导航的ViewHolder
 */
public class NavigationViewHolder extends BaseViewHolder<ItemNavigationBinding, NavigationItemViewModel> implements DetailsNavigator {

    public NavigationViewHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.item_navigation);
    }

    @Override
    protected void initViewModel() {
        mViewModel = new NavigationItemViewModel(this);
    }

    @Override
    protected void bindViewModel() {
       // mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        mDataBinding.tflFlowLayout.setAdapter(new NavigationTagAdapter());
        mDataBinding.tflFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {

            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mViewModel.onClickTag(position);
                return true;
            }
        });
    }


    @Override
    public void startDetailsActivity(String url) {

    }
}
