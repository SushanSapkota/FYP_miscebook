package com.fyp_miscebook.stetho

import android.app.Application
import com.facebook.stetho.Stetho
import com.fyp_miscebook.BuildConfig

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}