package com.example.sunnyweather.ui.home.weather


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Location

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()
    //重名的原因，注意导入对应的包
    var locationLng = ""
    var locationLat = ""
    var placeName = ""
    val weatherLiveData = locationLiveData.switchMap { location ->
        Repository.refreshWeather(location.lng,location.lat)
        //这个相当于给这个weatherLiveData赋值
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = Location(lng,lat)
        //这个方法是来修改locationLiveData的数据的，当它的数据被修改的时候
        //switchMap方法便会被重新执行一次，从而重新给refreshWeather数据
    }
}