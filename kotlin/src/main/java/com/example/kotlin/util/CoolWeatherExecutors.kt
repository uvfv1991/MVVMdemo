package com.example.kotlin.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
/**
 *  author : jiangxue
 *  date : 2023/5/31 16:53
 *  description :线程控制
 */
object CoolWeatherExecutors {

    val diskIO = Executors.newSingleThreadExecutor()

    val networkIO = Executors.newFixedThreadPool(3)

    val mainThread = MainThreadExecutor()

    class MainThreadExecutor : Executor {
        val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            if (command != null) {
                handler.post(command)
            }
        }
    }

}