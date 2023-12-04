package com.kiudysng.cmvh

import android.app.Application
import android.content.Context

class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
            set
    }
}