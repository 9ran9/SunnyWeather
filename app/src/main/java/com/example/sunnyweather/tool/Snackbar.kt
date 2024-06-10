package com.example.androidkotlin.tool

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * 拓展String和Int函数的Toast用法：
 * ”This is Toast“.showSnackBar(view)
 *  或： ”This is Toast".showSnackBar(view,Toast.LENGTH_LONG)
 *  或： ”This is Toast".showSnackBar(view,"Action"){ 具体的按钮逻辑 }
 */

fun String.showSnackBar(view: View,actionText:String? = null,
                        duration :Int = Snackbar.LENGTH_SHORT,block:( ()->Unit )? = null){
    //高阶函数的用法，只是没想到block也可以声明为空的
    val snackBar = Snackbar.make(view,this,duration)
    if (actionText != null && block != null){
        snackBar.setAction(actionText){
            block()
        }
    }
    snackBar.show()
}

fun Int.showSnackBar(view: View,actionText:String? = null,
                        duration :Int = Snackbar.LENGTH_SHORT,block:( ()->Unit )? = null){
    val snackBar = Snackbar.make(view,this,duration)
    if (actionText != null && block != null){
        snackBar.setAction(actionText){
            block()
        }
    }
    snackBar.show()
}
