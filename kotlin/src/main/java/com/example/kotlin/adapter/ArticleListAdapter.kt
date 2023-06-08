package com.example.kotlin.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlin.R
import com.example.kotlin.data.Article

//文章列表
    class ArticleListAdapter(val context: Context, datas: ArrayList<Article>) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article, datas) {
    override fun convert(helper: BaseViewHolder, item: Article?) {
        //不做处理返回 null执行return
        item ?: return
        @Suppress("DEPRECATION")

        helper.setVisible(R.id.tv_top,if (item.top) true else false)
        helper.setVisible(R.id.tv_fresh,if (item.fresh)true else false)
        helper.setText(R.id.tv_author, item.author)
            .setText(R.id.tv_title, item.title)
            .setText(R.id.tv_chapterName, item.chapterName)
            .addOnClickListener(R.id.tv_fresh)//添加点击监听
    }
}