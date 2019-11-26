package com.mitteloupe.solidcomponents

import android.app.Application
import com.mitteloupe.solidcomponents.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SampleSolidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SampleSolidApplication)
            modules(appModule)
        }
    }
}