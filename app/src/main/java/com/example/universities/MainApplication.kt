package com.example.universities

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        lateinit var mInstance: MainApplication

        @Synchronized
        fun getInstance(): MainApplication {
            return mInstance
        }
    }
}