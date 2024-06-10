package com.example.sunnyweather.logic.model

data class Weather(val realtime:RealtimeResponse.Realtime,val daily: DailyResponse.Daily)
//定义天气类，包含上面两个东西。