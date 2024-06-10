package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import com.example.sunnyweather.tool.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


object Repository {
    //存储库
    /**
     * 获取地区：
     */
    fun searchPlaces(query:String) = fire(Dispatchers.IO){
        //liveData : 返回一个liveData,可以调用挂起函数
        //Dispatchers:调度：将如下逻辑跳转到子线程。网络请求都只能在子线程进行
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status == "ok"){ //这个 ok 也是对应服务器的东西
                val places = placeResponse.places
                Result.success(places)  //包装对应数据
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }

    }

    /**
     * 获取天气：
     */
    fun refreshWeather(lng:String,lat:String) =  fire(Dispatchers.IO){
        // 原来Dispatchers.IO的类是：coroutineContext,coroutine:协程，
        // 也就是说，协程好像是一个另一
            coroutineScope {
                //创建一个协程作用域
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                //async 异步处理
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                //这一段如何是好啊
                if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime,
                        dailyResponse.result.daily)

                    Result.success(weather)
                }else{
                    "Repository".logD("real.status is ${realtimeResponse.status}")
                    "Repository".logD("daily.status is ${dailyResponse.status}")
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}"+
                            "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
    }
    /**
     *  统一try-catch，简化代码
     */
   private fun <T>fire(context: CoroutineContext,block:suspend ()->Result<T>)=
       liveData(context) {  //这个suspend是挂起声明
           val result = try {
               block()  //这样才是调用
           }catch (e:Exception){
               Result.failure(e)
           }
           emit(result)
       }


    /**
     * 记录选中城市：
     */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


}