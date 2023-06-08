package com.example.kotlin.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.kotlin.R
import com.example.kotlin.ui.MainActivity

/**
 * author : jiangxue
 * date : 2023/5/15 9:09
 * description :
 */
class AppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        context = this
        MultiDex.install(this)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}