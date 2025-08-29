package com.example.helppet

import android.app.Application
import com.example.helppet.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class HelpPetApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HelpPetApplication)
            modules(appModule)
        }
    }
}