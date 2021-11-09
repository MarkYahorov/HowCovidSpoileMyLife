package com.example.howcovidspoilemylife.core

import android.app.Application
import com.example.howcovidspoilemylife.core.di.AppComponent
import com.example.howcovidspoilemylife.core.di.DaggerAppComponent

class CovidApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}