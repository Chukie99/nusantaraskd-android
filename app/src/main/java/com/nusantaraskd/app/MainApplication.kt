package com.nusantaraskd.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: Application? = null
        fun getAppContext(): Application {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }
}
