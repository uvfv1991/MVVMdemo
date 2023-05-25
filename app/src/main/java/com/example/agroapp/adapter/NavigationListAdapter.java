package com.example.agroapp.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;

import com.example.agroapp.model.Navigation;
import com.example.agroapp.viewholder.NavigationViewHolder;


/**
 * 导航列表Adapter
 */
public class NavigationListAdapter extends BaseAdapter<NavigationViewHolder> {

    public NavigationListAdapter(@NonNull ObservableList<Object> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavigationViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationViewHolder holder, int position) {
        Object data = mDataList.get(position);
        //holder.getViewModel().setBaseModel((Navigation) data);
    }
}
