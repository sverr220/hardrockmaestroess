package ru.directum.maestro.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ru.directum.maestro.di.initKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}