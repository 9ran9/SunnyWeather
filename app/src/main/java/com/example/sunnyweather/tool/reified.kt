package com.example.sunnyweather.tool

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

//reified:实列化
//由于T是泛型，如果写一个方法，那么是不可能知道T是什么类型，自然就不能T::class.java，可是我的方法（val a = a is T）要用啊
//于是：inline把它放到了具体位置上，再reified得到具体的类型，于是就可以T::class.java或者 a is T
//同时由于实列化不可为空，故声明block不为空

@SuppressLint("SuspiciousIndentation")
inline fun <reified T>startActivity(context: Context, noinline block:(Intent.() -> Unit)?=null){
                                               //高阶函数（传入的数据类型）->传出的数据类型。
    val intent = Intent(context,T::class.java)
    if (block!= null)
    intent.block()
    context.startActivity(intent)
}
//启动服务
@SuppressLint("SuspiciousIndentation")
inline fun <reified T>startService(context: Context, noinline block:(Intent.() -> Unit)?=null){
    val intent = Intent(context,T::class.java)
    if (block != null)
    intent.block()
    context.startService(intent)
}