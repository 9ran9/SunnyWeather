package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class DailyResponse(val status:String,val result:Result){//这里敲错了...完美
    //源数据是数组，所以这里用List进行映射
    //依旧是依据对应的写法进行class的编写：
    // ”temperature“:[ {"max":25.7,"min":20.3}... ]
    //这里表明温度是数组，所以在daily里面对温度声明数组
    //温度里面的 max:25.7是 表明是浮点数，所以在温度里面声明浮点数
    data class Result(val daily:Daily)
    data class Daily(val temperature:List<Temperature>,val skycon:List<Skycon>,
        @SerializedName("life_index") val lifeIndex:LifeIndex)
    data class Temperature(val max:Float,val min:Float)
    data class Skycon(val value:String,val date:Date)
    data class LifeIndex(val coldRisk:List<LifeDescription>,val carWashing:
    List<LifeDescription>,val ultraviolet:List<LifeDescription>,val dressing:List<LifeDescription>)
    data class LifeDescription(val desc:String)
    //这里的LifeDescription好像原格式没有写，但是它与其他的不同点在于它的下线都是desc这个可以注意下。
}