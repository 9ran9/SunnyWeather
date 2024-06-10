package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

//这些数据类型按照对应的接口信息来完成的，
//由于太老了，可以去对应的服务器去修改
data class PlaceResponse(var status:String, val places:List<Place>)

data class Place(val name:String,val location:Location,
    @SerializedName("formatted_address") val address: String)
//由于原本是formatted_address，这里用了address，做一个注解。

data class Location(val lng:String,val lat:String)
//lng:经度 lat:纬度