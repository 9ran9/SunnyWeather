package com.example.sunnyweather.tool

import android.content.Context
import android.widget.Toast

/**
 * 拓展String和Int函数的Toast用法：
 * ”This is Toast“.showToast(context)
 *  或： ”This is Toast".showToast(context,Toast.LENGTH_LONG)
 */
fun String.showToast(context: Context,duration:Int = Toast.LENGTH_SHORT){
    //设置默认值，这样就保证了Toast的完整性,全局context挺der的其实。
    Toast.makeText(context,this,duration).show()
}
fun Int.showToast(context: Context,duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(context,this,duration).show()
}
