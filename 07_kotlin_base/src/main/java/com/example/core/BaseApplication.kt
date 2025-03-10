package com.example.core

import android.app.Application
import android.content.Context
import com.example.core.BaseApplication

class BaseApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        currentApplication = this
    }

    companion object {
        private var currentApplication: Context? = null
        @JvmStatic
        fun currentApplication(): Context {
            return currentApplication!!
        }
    }
}