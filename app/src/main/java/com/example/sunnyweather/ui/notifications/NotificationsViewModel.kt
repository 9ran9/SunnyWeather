package com.example.sunnyweather.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "这个也没写，名字都没改，有什么好看的？？"
    }
    val text: LiveData<String> = _text
}