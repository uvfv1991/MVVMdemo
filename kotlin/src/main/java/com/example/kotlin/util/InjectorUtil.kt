package com.example.kotlin.util

import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.app.AppViewModelFactory
import com.example.kotlin.base.AppRepository
import com.example.kotlin.http.AgroNetWork

/**
 *  author : jiangxue
 *  date : 2023/5/31 16:53
 *  description :仓库注入工具
 */
object InjectorUtil {
    //主页
    fun getHomeViewModelFactory() = AppViewModelFactory(AppRepository.getInstance( AgroNetWork.getInstance()))

   /* fun getMainModelFactory() = MainModelFactory(getWeatherRepository())

    private fun getPlaceRepository() = PlaceRepository.getInstance(CoolWeatherDatabase.getPlaceDao(), CoolWeatherNetwork.getInstance())

    private fun getWeatherRepository() = WeatherRepository.getInstance(CoolWeatherDatabase.getWeatherDao(), CoolWeatherNetwork.getInstance())

    fun getChooseAreaModelFactory() = ChooseAreaModelFactory(getPlaceRepository())

    fun getWeatherModelFactory() = WeatherModelFactory(getWeatherRepository())

    fun getMainModelFactory() = MainModelFactory(getWeatherRepository())*/

}