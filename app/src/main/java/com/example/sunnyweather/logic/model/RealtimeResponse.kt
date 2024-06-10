package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status:String,val result: Result){
    data class Result(val realtime:Realtime)  //这里获取result下的东西
    data class Realtime(val skycon:String,val temperature: Float,
        @SerializedName("air_quality")val airQuality:AirQuality)  //是你吗？？
    //skycon:天气状况
    data class AirQuality(val aqi:AQI)
    //空气质量
    data class AQI(val chn:Float)
    //chn:中国空气质量指数
}
