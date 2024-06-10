package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.DailyResponse
import com.example.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    //大概对应的接口:https://api.caiyunapp.com/v2.5/sxavdSA2kt8736qH/101.6656,39.2072/realtime.json
    //大概对应的接口:https://api.caiyunapp.com/v2.5/sxavdSA2kt8736qH/101.6656,39.2072/daily.json
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String):Call<RealtimeResponse>
    //{}占位符 @Path 处理对应的占位符，不同的接口写法对应不同的请求写法。

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String,@Path("lat")lat: String):Call<DailyResponse>

}