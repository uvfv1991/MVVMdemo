package com.example.agroapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.agroapp.R;
import com.example.agroapp.model.ModelBanner;
import com.example.agroapp.model.ModelCategoryCart;
import com.example.agroapp.model.ModelSubjectCard;
import com.example.agroapp.model.ModelTitleView;
import com.example.agroapp.model.ModelTopBanner;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;


/**
 * 发现Adapter
 */
public class NominateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int CATEGOROY_CART = 1;
    private int SUBJECT_CART = 2;
    private int TEXTVIEW = 3;
    private int BANNER = 4;
    private int VIDEOSMALLCARD = 5;
    private int BRIEFCARD = 6;
    private int SCROLL_BANNER = 7;

    Context context;
    ArrayList<Object> data;

    public NominateAdapter(Context context, ArrayList<Object> dataList) {
       this.context=context;
       this.data=dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof ModelCategoryCart) {
            return CATEGOROY_CART;
        }else  if (data.get(position) instanceof ModelSubjectCard) {
            return SUBJECT_CART;
        }else  if (data.get(position) instanceof ModelTitleView) {
            return this.TEXTVIEW;
        }else  if (data.get(position) instanceof ModelBanner) {
            return BANNER;
        }else  if (data.get(position) instanceof ModelTopBanner) {
            return SCROLL_BANNER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == this.CATEGOROY_CART) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_discover_category, parent, false);
          return  new CategoryCartViewHolder(view);

        } else if (viewType == this.SUBJECT_CART) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_discover_subject, parent, false);
            return  new SubjectCardViewHolder(view);

        } else if (viewType == this.TEXTVIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_title, parent, false);
            return  new TitleViewViewHolder(view);

        } else if (viewType == this.BANNER) {
            view = LayoutInflater.from(context).inflate(R.layout.adatper_discover_banner, parent, false);
            return  new BannerViewHolder(view);

        } else if (viewType == this.VIDEOSMALLCARD) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_small_video, parent, false);

            return  new VideoSmallCardViewHolder(view);
        } else if (viewType == this.BRIEFCARD) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_brief_layout, parent, false);
            return  new BriefcardViewHolder(view);

        } else if (viewType == this.SCROLL_BANNER) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_scroll_banner, parent, false);

            return  new ScrollBannerViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_brief_layout, parent, false);

            return  new BriefcardViewHolder(view);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //spancount  网格的列数
        if (data.get(position) instanceof ModelCategoryCart) {
           if (holder instanceof CategoryCartViewHolder){//热门分类
               ModelCategoryCart modelCategoryCart = (ModelCategoryCart) data.get(position);
               ((CategoryCartViewHolder) holder).tvTitle.setText(modelCategoryCart.getData().getHeader().getTitle());
               ((CategoryCartViewHolder) holder).tvMore.setText(modelCategoryCart.getData().getHeader().getRightText());
               ((CategoryCartViewHolder) holder).rvCategory.setAdapter(new CategoryAdapter(R.layout.adapter_category, modelCategoryCart.getData().getItemList()));
               GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2,GridLayoutManager.HORIZONTAL, false);
               ((CategoryCartViewHolder) holder).rvCategory.setLayoutManager(gridLayoutManager);

           }
        }else  if (data.get(position) instanceof ModelSubjectCard) {//专题策划
            if (holder instanceof SubjectCardViewHolder){
                ModelSubjectCard modelCategoryCart = (ModelSubjectCard) data.get(position);
                ((SubjectCardViewHolder) holder).tvTitle.setText(modelCategoryCart.getData().getHeader().getTitle());
                ((SubjectCardViewHolder) holder).tvMore.setText(modelCategoryCart.getData().getHeader().getRightText());
                ((SubjectCardViewHolder) holder).rvSubject.setAdapter(new SubjectAdapter(R.layout.adapter_subject_cart, modelCategoryCart.getData().getItemList()));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2,GridLayoutManager.VERTICAL, false);
                ((SubjectCardViewHolder) holder).rvSubject.setLayoutManager(gridLayoutManager);

            }

        }else  if (data.get(position) instanceof ModelTitleView) {
            if (holder instanceof TitleViewViewHolder){
                ModelTitleView modelCategoryCart = (ModelTitleView) data.get(position);
                ((TitleViewViewHolder) holder).tvTitle.setText(modelCategoryCart.getData().getText());
                ((TitleViewViewHolder) holder).tvMore.setText(modelCategoryCart.getData().getRightText());


            }

        }else  if (data.get(position) instanceof ModelBanner) {
            if (holder instanceof BannerViewHolder){
                ModelBanner modelCategoryCart = (ModelBanner) data.get(position);
                Glide.with(context).load(modelCategoryCart.getData().getImage()).into(((BannerViewHolder) holder).getImBanner());
            }


        }else  if (data.get(position) instanceof ModelTopBanner) {//顶层的轮播图
            if (holder instanceof  ScrollBannerViewHolder){
                ModelTopBanner banner = (ModelTopBanner) data.get(position);


                (( ScrollBannerViewHolder) holder).cbBanner.setIndicator(new CircleIndicator(context));
                (( ScrollBannerViewHolder) holder).cbBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                (( ScrollBannerViewHolder) holder).cbBanner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));
                (( ScrollBannerViewHolder) holder).cbBanner.setAdapter(new BannerImageAdapter<ModelTopBanner.Data.Item>(banner.getData().getItemList()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, ModelTopBanner.Data.Item data, int position, int size) {
                        //图片加载自己实现
                        Glide.with(holder.itemView)
                                .load(data.getData().getImage())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);

                    }

                });

            }

        }
    }

    @Override
    public int getItemCount() {
        return data != null?data.size():0;
    }


    public final class CategoryCartViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private RecyclerView rvCategory;
        @Nullable
        private TextView tvTitle;
        @Nullable
        private TextView tvMore;


        public CategoryCartViewHolder( View itemView) {
            super(itemView);
            this.tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            this.tvMore = (TextView)itemView.findViewById(R.id.tv_more);
            this.rvCategory = (RecyclerView)itemView.findViewById(R.id.rv_category);
        }
    }

    public final class SubjectCardViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private RecyclerView rvSubject;
        @Nullable
        private TextView tvTitle;
        @Nullable
        private TextView tvMore;

        public SubjectCardViewHolder( View itemView) {
            super(itemView);
            this.tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            this.tvMore = (TextView)itemView.findViewById(R.id.tv_more);
            this.rvSubject = (RecyclerView)itemView.findViewById(R.id.rv_subject);
        }
    }


    public final class TitleViewViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private TextView tvTitle;
        @Nullable
        private TextView tvMore;


        public TitleViewViewHolder(View itemView) {

            super(itemView);
            this.tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            this.tvMore = (TextView)itemView.findViewById(R.id.tv_more);
        }
    }

    public final class BannerViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private ImageView imBanner;

        @Nullable
        public final ImageView getImBanner() {
            return this.imBanner;
        }

        public final void setImBanner(@Nullable ImageView var1) {
            this.imBanner = var1;
        }

        public BannerViewHolder( View itemView) {
            super(itemView);
            this.imBanner = (ImageView)itemView.findViewById(R.id.im_banner);
        }
    }


    public final class VideoSmallCardViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private TextView tvTitle;
        @Nullable
        private ImageView imCover;
        @Nullable
        private TextView tvDesc;

        public VideoSmallCardViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            this.tvDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            this.imCover = (ImageView)itemView.findViewById(R.id.im_cover);
        }
    }



    public final class BriefcardViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private TextView tvTitle;
        @Nullable
        private ImageView imCover;
        @Nullable
        private TextView tvDesc;

        public BriefcardViewHolder( View itemView) {
            super(itemView);
            this.tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            this.tvDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            this.imCover = (ImageView)itemView.findViewById(R.id.im_cover);
        }
    }


    public final class ScrollBannerViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private Banner cbBanner;

        @Nullable
        public final Banner getCbBanner() {
            return this.cbBanner;
        }

        public final void setCbBanner(@Nullable Banner  var1) {
            this.cbBanner = var1;
        }

        public ScrollBannerViewHolder(View itemView) {
            super(itemView);
            this.cbBanner = (Banner)itemView.findViewById(R.id.cb_banner);
        }
    }
}
