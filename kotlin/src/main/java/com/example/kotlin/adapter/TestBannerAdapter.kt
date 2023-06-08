package com.example.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.R
import com.example.kotlin.data.Banner
import com.youth.banner.adapter.BannerAdapter
import java.lang.reflect.Constructor

/**
 *  author : jiangxue
 *  date : 2023/6/7 14:44
 *  description :
 */
class TestBannerAdapter(data: List<Banner>) :
        BannerAdapter<Banner, TestBannerAdapter.BannerViewHolder>(data) {


    class BannerViewHolder(view: View, img: ImageView, title: TextView) :
            RecyclerView.ViewHolder(view) {

        var imageView: ImageView = img
        var title: TextView = title
    }

    override fun onCreateHolder(p0: ViewGroup, p1: Int): BannerViewHolder {

        val headerView =
                LayoutInflater.from(p0.context).inflate(R.layout.banner_image_title_num, p0, false)
        headerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        val imageView = headerView.findViewById<ImageView>(R.id.image)
        val title = headerView.findViewById<TextView>(R.id.bannerTitle)

        return BannerViewHolder(headerView, imageView, title)
    }

    override fun onBindView(p0: BannerViewHolder, p1: Banner, p2: Int, p3: Int) {

        Glide.with(p0.imageView.context).load(p1.url).into(p0.imageView)
        p0.title.text = p1.title


    }

}
