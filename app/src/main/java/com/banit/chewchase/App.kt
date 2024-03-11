package com.banit.chewchase

import android.app.Application
import com.banit.chewchase.data.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var appInitializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        application = this
        Timber.plant(Timber.DebugTree())
        appInitializer.init()
    }

    companion object {
        lateinit var application: Application
            private set
    }
}