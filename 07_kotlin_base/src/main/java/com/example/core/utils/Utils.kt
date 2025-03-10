package com.example.core.utils

import android.widget.Toast
import com.example.core.BaseApplication
import android.util.DisplayMetrics
import android.content.res.Resources
import android.util.TypedValue

object Utils {
    @JvmStatic
    @JvmOverloads
    fun toast(string: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(BaseApplication.currentApplication(), string, duration).show()
    }

    private val displayMetrics = Resources.getSystem().displayMetrics
    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    }
}