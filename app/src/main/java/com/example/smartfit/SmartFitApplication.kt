package com.example.smartfit

import android.app.Application
import com.example.smartfit.di.AppGraph

class SmartFitApplication : Application() {
    lateinit var graph: AppGraph
    override fun onCreate() {
        super.onCreate()
        graph = AppGraph(this)
    }
}
