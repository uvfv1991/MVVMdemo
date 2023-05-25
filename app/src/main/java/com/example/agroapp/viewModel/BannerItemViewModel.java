package com.example.agroapp.viewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.agroapp.model.Article;
import com.example.agroapp.model.BannerData;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by jx
 * 轮播
 */

public class BannerItemViewModel extends ItemViewModel<HomeViewModel> {
    public ObservableField<BannerData> entity = new ObservableField<>();

    public BannerItemViewModel(@NonNull HomeViewModel viewModel, BannerData entity) {
        super(viewModel);
        this.entity.set(entity);

    }

    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    public int getPosition() {
        return 0;
        //return viewModel.getItemPosition(this);
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            //拿到position
            int position = viewModel.dataList.indexOf(BannerItemViewModel.this);
            ToastUtils.showShort("position：" + position);

        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //以前是使用Messenger发送事件，在WeatherViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, WeatherViewModel.TOKEN_WeatherViewModel_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
            //ToastUtils.showShort(entity.get().g);
        }
    });
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
