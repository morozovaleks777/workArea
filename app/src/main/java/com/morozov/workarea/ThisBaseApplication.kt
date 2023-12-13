package com.morozov.workarea

import android.app.Application
import com.squareup.leakcanary.core.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ThisBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

     //   if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
      //  }
    }
}