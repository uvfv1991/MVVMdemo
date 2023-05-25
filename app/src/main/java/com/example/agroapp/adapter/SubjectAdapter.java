package com.example.agroapp.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.agroapp.R;
import com.example.agroapp.model.ModelCategoryCart;
import com.example.agroapp.model.ModelSubjectCard;
import com.example.agroapp.utils.Util;

import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/24 16:46
 * description :  专题策划 （ 动态设置布局LayoutParams）
 */
public class SubjectAdapter extends BaseQuickAdapter<ModelSubjectCard.Data.Item, BaseViewHolder> {

    public SubjectAdapter(int layoutResId, @Nullable List<ModelSubjectCard.Data.Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ModelSubjectCard.Data.Item item) {
        helper.setText(R.id.tv_category_name,item.getData().getTitle());
        ConstraintLayout container = helper.getView(R.id.cl_subject);
        ImageView imageView = (ImageView) helper.getView(R.id.im_cover);
//setMargins方法同时设置上下左右四个方向的间距，

        ConstraintLayout.LayoutParams lp;
        if((helper.getAdapterPosition()+1)%2!=0){
            lp= new ConstraintLayout.LayoutParams(Util.getWindowMetrics(mContext)[0]/2- Util.dpTopx(10),Util.dpTopx(60));
            lp.setMargins(0,0,Util.dpTopx(10),Util.dpTopx(5));//设置右下的间矩  0   2
        }else{
            lp= new ConstraintLayout.LayoutParams(Util.getWindowMetrics(mContext)[0]/2-Util.dpTopx(10),Util.dpTopx(60));
            lp.setMargins(Util.dpTopx(10),0,0,Util.dpTopx(5));//设置左下的间矩  1  3
        }

        container.setLayoutParams(lp);
        Glide.with(imageView).setDefaultRequestOptions(new RequestOptions().frame(3000000).centerCrop()).load(item.getData().getImage()).into(imageView);



    }


}
