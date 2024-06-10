package com.example.sunnyweather.ui.home.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place

class PlaceViewModel:ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()  //缓存一些城市信息
    //在碎片哪里得到初始化

    //13.4.2（550页）：还有个map，减少被观察的数据的。
    val placeListData = searchLiveData.switchMap {query ->
        //由于每次的LiveData都是新的，所以调用这个方法，获取新的LiveData来让活动观察
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String){
        searchLiveData.value = query
    }

    fun savePlace(place: Place)  = Repository.savePlace(place)
    fun getSavePlace() = Repository.getSavePlace()
    fun isPlaceSave() = Repository.isPlaceSaved()
}