package com.coolya.daunick

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.coolya.daunick.di.DataBaseModule
import com.coolya.daunick.di.DiaryEventModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(DataBaseModule, DiaryEventModule))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}