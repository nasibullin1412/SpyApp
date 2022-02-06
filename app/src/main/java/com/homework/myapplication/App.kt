package com.homework.myapplication

import android.app.Application
import android.content.Context
import com.homework.myapplication.data.frameworks.ApiService

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    val apiService: ApiService by lazy { ApiService.create() }

    companion object {
        lateinit var appContext: Context
            private set
        lateinit var instance: App
            private set
    }
}
