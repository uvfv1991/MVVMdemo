package com.example.kotlin.base

import android.util.Log
import com.example.kotlin.data.Article
import com.example.kotlin.data.ArticleList
import com.example.kotlin.http.AgroNetWork
import com.example.kotlin.http.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

/**
 *  author : jiangxue
 *  date : 2023/6/1 15:59
 *  description :数据仓库(可以扩展本地和网络的数据)
 */

class AppRepository private constructor(private val network: AgroNetWork){
    /*
    1.这个函数不会阻塞当前调用的线程
    2.suspend 用于暂停执行当前协程，并保存所有局部变量，被标记为 suspend 的函数只能运行在协程或者其他 suspend 函数。
    3.无须关心这个函数内部是在哪个线程执行
    4.挂起函数可以执行长时间运行的操作并等待它完成而不会阻塞主线程。
    aunch 是 CoroutineScope 的一个扩展函数，该方法在不阻塞当前线程的情况下启动新的协程，launch 里面的代码虽然有挂起函数，但还是会按顺序运行（注意，这里的挂起函数并没有用withContext选择去指定切换的线程）；
coroutineScope 本身就是一个挂起函数，会挂起当前的协程。coroutineScope 里面的代码除了 launch，其他按照顺序运行，而 coroutineScope 里面可以 launch 多个 job，这多个 job 是并行的；
suspend 挂起函数里面的挂起函数是（默认）串行的（即，用同步的方式实现异步）。
————————————————
    }*/
    //val相当于java中定义常量时加了一个final，而var就是正常定义变量
     suspend fun getArticleListData(pageNum: Int): ArticleList {
        var list = requestArticleListData(pageNum)

        return  list
    }
    //通过 withContext 我们把主线程挪到了后台线程池中(不创建新的协程，指定协程上运行代码块)
     suspend fun requestArticleListData(pageNum: Int) = withContext(Dispatchers.IO) {//切换线程
        val response = network.requestArticle(pageNum)
        return@withContext response.data
    }


    suspend fun requestBannerData() = withContext(Dispatchers.IO) {//切换线程
        val response = network.requestBanner()
        return@withContext response.data
    }

    //一个类里面只能声明一个内部关联对象，即关键字 companion 只能使用一次。直接通过外部类访问到对象的内部元素


    companion object {

        private lateinit var instance:AppRepository

        fun getInstance(network: AgroNetWork): AppRepository {
            if (!::instance.isInitialized) {
                synchronized(AppRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = AppRepository(network)
                    }
                }
            }
            return instance
        }

    }

}