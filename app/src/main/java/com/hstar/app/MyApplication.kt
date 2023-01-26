package com.hstar.app

import android.app.Application
import com.hstar.base.BuildConfig
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MyApplication : Application() {

    private val timberDebugTree = object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "${element.fileName}:${element.lineNumber}#${element.methodName}"
        }
    }

    override fun onCreate() {
        super.onCreate()

        Hawk.init(applicationContext).build()
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
            Timber.plant(timberDebugTree)
        }
    }



}