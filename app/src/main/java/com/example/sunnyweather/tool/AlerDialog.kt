package com.example.sunnyweather.tool

import android.app.AlertDialog
import android.content.Context

 fun hintDialog(context: Context,message:String = "",
               isCancelable:Boolean = true,block:( ()->Unit )?=null){
    AlertDialog.Builder(context).apply {
        setTitle("提示")
        setMessage(message)
        setCancelable(isCancelable)
        if (block!=null){
            //对于高阶函数的封装，我们可以先把原来的用法写出来
            //再观察那些是我要封装的，那些是可以封装的。
            setPositiveButton("ok"){dialog,_->
                    block()
                dialog.dismiss()
            }
        }
        show()

    }
}

