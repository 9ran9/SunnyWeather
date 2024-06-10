package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    //为了与这个网址 接口进行交互:
    //v2/place?query=北京&token={token}&lang=zh_CN
    //所以，下面的查询的@Query("query")表明，这个query的值
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
        //这个接口查询对应的城市，然后获取它的经纬度后，再获取对应的天气信息
    fun searchPlaces(@Query("query")query: String) : Call<PlaceResponse>



}