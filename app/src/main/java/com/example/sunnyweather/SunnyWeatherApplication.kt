package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object{
        const val TOKEN = "sxavdSA2kt8736qH"  //const:表示常量，就是用于全局的
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        //定义单列，全局获取
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}