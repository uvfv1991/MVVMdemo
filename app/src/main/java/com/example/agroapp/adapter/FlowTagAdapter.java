package com.example.agroapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.agroapp.R;
import com.example.agroapp.model.Article;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;

/**
 * author : jiangxue
 * date : 2023/5/23 16:12
 * description :
 */
public class FlowTagAdapter extends TagAdapter {
    private ArrayList<Article> datalist;
    private Context context;

    public FlowTagAdapter(Context mcontext,ArrayList<Article> datas) {
        super(datas);
        this.context = mcontext;
        this.datalist = datas;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {
        TextView tv = (TextView) View.inflate(context, R.layout.tagflow_tv_context, null);
        tv.setText(datalist.get(position).getChapterName());
        return tv;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
    }

    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);
    }
}
