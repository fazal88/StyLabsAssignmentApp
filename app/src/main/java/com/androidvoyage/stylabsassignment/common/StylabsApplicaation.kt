package com.androidvoyage.stylabsassignment.common

import android.app.Application
import android.content.Context

class StylabsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {


        @Volatile
        var appContext: Context? = null
            private set
    }
}