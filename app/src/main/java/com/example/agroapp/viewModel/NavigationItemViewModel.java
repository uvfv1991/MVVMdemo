package com.example.agroapp.viewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.agroapp.base.BaseItemViewModel;
import com.example.agroapp.model.Article;
import com.example.agroapp.model.Navigation;
import com.example.agroapp.navigator.DetailsNavigator;

/**
 * author : jiangxue
 * date : 2023/5/22 16:14
 * description :获取导航数据
 */


public class NavigationItemViewModel extends BaseItemViewModel<Navigation> {

public final ObservableField<String> name = new ObservableField<>();

public final ObservableList<Article> dataList = new ObservableArrayList<>();

private DetailsNavigator mDetailsNavigator;

public NavigationItemViewModel(@NonNull DetailsNavigator detailsNavigator) {
    mDetailsNavigator = detailsNavigator;
}

@Override
protected void setAllModel(@NonNull Navigation navigation) {
    name.set(navigation.getName());
    dataList.addAll(navigation.getArticles());
}

public void onClickTag(int position) {
    if (dataList.size() > position) {
        Article article = dataList.get(position);
        mDetailsNavigator.startDetailsActivity(article.getLink());
    }
}
}
