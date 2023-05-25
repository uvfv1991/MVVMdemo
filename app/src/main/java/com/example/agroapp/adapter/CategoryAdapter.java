package com.example.agroapp.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.agroapp.R;
import com.example.agroapp.model.ModelCategoryCart;

import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/24 16:46
 * description :热闹分类
 */
public class CategoryAdapter extends BaseQuickAdapter<ModelCategoryCart.Data.Item, BaseViewHolder> {

    public CategoryAdapter(int layoutResId, @Nullable List<ModelCategoryCart.Data.Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ModelCategoryCart.Data.Item item) {
        helper.setText(R.id.tv_category_name,item.getData().getTitle());
        Glide.with(mContext).load(item.getData().getImage()).into((ImageView) helper.getView(R.id.im_cover));
    }


}
