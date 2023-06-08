package com.example.kotlin.app

/**
 * 常量
 */
interface Constant {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
        const val ABOUT_URL = "https://github.com/chongyucaiyan/WanAndroid-Java"
        const val KEY_URL = "key_url"
        const val KEY_ID = "key_id"
        const val KEY_CHAPTER = "key_chapter"
        const val SPLASH_TIME: Long = 2000
        const val EXIT_TIME: Long = 2000
        const val TIMEOUT_CONNECT: Long = 10
        const val TIMEOUT_READ: Long = 10
        const val TIMEOUT_WRITE: Long = 10
    }
}