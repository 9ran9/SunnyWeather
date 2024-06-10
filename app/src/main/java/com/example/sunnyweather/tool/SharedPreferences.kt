package com.example.sunnyweather.tool

import android.content.SharedPreferences


/**
 * 优化SharedPreferences存储方法的使用：
 * sp.plus{简化了每一步都要editor和最后一步的apply}
 */

inline fun SharedPreferences.plus(block:SharedPreferences.Editor.() ->Unit){
    val editor = edit()
    //获取对应的Editor对象
    editor.block()
    //表示使用对应的函数
    editor.apply()
    //让我们在写的时候少写apply。
}