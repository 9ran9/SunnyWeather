package com.example.sunnyweather.logic.network

import com.example.sunnyweather.tool.logD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object SunnyWeatherNetwork {

    /**
     *天气
     */
    private val weatherService = ServiceCreator.create<WeatherService>()
    //得到对应的返回值：
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng,lat).await()


    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng,lat).await()


    /**
     * 地区：
     */
    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    //suspend  : 挂起，

    /**
     *Call<T>.await函数，简化回调
     */

    //简化回调：
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            //挂起当前协程，直到如下完成：
            enqueue(object : Callback<T> {
                //异步处理队列，处理Callback这个函数的逻辑
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)  //resume 恢复挂起的协程，并返回body
                    else {
                        it.resumeWithException(
                            RuntimeException("response body is null")
                        )
                        "await的回调".logD("响应的主体是空的")
                    }
                }

                override fun onFailure(p0: Call<T>, p1: Throwable) {
                    it.resumeWithException(p1)
                }
            })
        }
    }

}