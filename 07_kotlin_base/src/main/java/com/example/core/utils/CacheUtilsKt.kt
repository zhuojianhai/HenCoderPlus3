package com.example.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.core.BaseApplication
import com.example.core.R

/**
 * author zjh
 * date 2025/3/4 10:39
 * desc
 */
object CacheUtilsKtKt {
    private val  context:Context = BaseApplication.currentApplication()

    val SP:SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    fun save(key:String,value:String){
        SP.edit().putString(key,value).apply()
    }
    fun get(key:String):String?{
      return  SP.getString(key,null)
    }
}