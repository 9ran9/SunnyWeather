package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    //服务构建器,这个确实还需要好好复习一下的
    private const val BASE_URL = " https://api.caiyunapp.com/"  //这个也是接口的前一部分

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //创建retrofit，接收::claas.java数据
    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)

    //内联优化掉::class.java
    inline fun <reified T> create():T = create(T::class.java)


}