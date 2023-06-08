package com.example.kotlin.http

import com.example.kotlin.data.ArticleList
import com.example.kotlin.http.api.HomeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *  author : jiangxue
 *  date : 2023/6/1 16:41
 *  description :
 */

class AgroNetWork {

    private val service = ServiceCreator.create(HomeService::class.java)

    //首页数据  await()在kotlin中是一个挂起函数，他会等待结果返回才继续执行之后的代
    suspend fun requestArticle(pageNum: Int) = service.getArticleListData(pageNum).await()
    //获取轮播图
    suspend fun requestBanner() = service.getBannerData().await()


    //这时是做了一个整体的处理
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: AgroNetWork? = null

        fun getInstance(): AgroNetWork {
            if (network == null) {
                synchronized(AgroNetWork::class.java) {
                    if (network == null) {
                        network = AgroNetWork()
                    }
                }
            }
            return network!!////抛出空指针异常
        }

    }

}



