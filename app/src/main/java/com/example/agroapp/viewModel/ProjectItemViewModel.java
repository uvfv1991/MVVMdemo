package com.example.agroapp.viewModel;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.agroapp.model.Article;
import com.example.agroapp.navigator.DetailsNavigator;
import com.example.agroapp.ui.main.activity.DetailsActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by jx
 */

public class ProjectItemViewModel extends ItemViewModel<ProjectListViewModel> implements DetailsNavigator {
    public ObservableField<Article> entity = new ObservableField<>();
    public ProjectItemViewModel(@NonNull ProjectListViewModel viewModel, Article entity) {
        super(viewModel);
        this.entity.set(entity);

        //ImageView的占位图片，可以解决RecyclerView中图片错误问题

    }

    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    public int getPosition() {
        return viewModel.getItemPosition(this);
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            //拿到position
            //int position = viewModel.dataList.indexOf(ProjectItemViewModel.this);
            ToastUtils.showShort("position：" + getPosition());
            /*vm+view 不要直接交互*/

            if (entity.get().getLink() != null && !TextUtils.isEmpty(entity.get().getLink())) {
                //跳转到详情界面,传入条目的实体对象
                //mDetailsNavigator.startDetailsActivity(entity.get().getLink());
                viewModel.uc.itemclick.setValue(entity.get().getLink());

            }

        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("长按一下就删除" );
            viewModel.uc.deleteitemclick.setValue(ProjectItemViewModel.this);
            //viewModel.uc.deleteitemclick.setValue(getPosition());
        }
    });

    @Override
    public void startDetailsActivity(String url) {

    }
//    /**
//     * 可以在xml中使用binding:currentView="@{viewModel.titleTextView}" 拿到这个控件的引用, 但是强烈不推荐这样做，避免内存泄漏
//     **/
//    private TextView tv;
//    //将标题TextView控件回调到ViewModel中
//    public BindingCommand<TextView> titleTextView = new BindingCommand(new BindingConsumer<TextView>() {
//        @Override
//        public void call(TextView tv) {
//            NetWorkItemViewModel.this.tv = tv;
//        }
//    });
}
