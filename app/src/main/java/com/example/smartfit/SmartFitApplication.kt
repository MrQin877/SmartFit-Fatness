package com.example.smartfit

import android.app.Application
import com.example.smartfit.data.local.SmartFitDatabase
import com.example.smartfit.data.repository.ActivityRepository
import com.example.smartfit.data.repository.TipsRepository
import com.example.smartfit.data.remote.ApiService
import com.example.smartfit.data.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SmartFitApplication : Application() {
    // Initialize your dependency container once for the whole app
    val appContainer: AppContainer by lazy {
        AppContainer(this)
    }
}

class AppContainer(application: Application) {
    // Room DB
    val database = SmartFitDatabase.create(application)

    // Repositories
    val activityRepository = ActivityRepository(database.activityDao())
    val userRepository = UserRepository(database.userDao())   // <-- ✅ Add this line

    // Retrofit + tips repo
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/") // example API
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)
    val tipsRepository = TipsRepository(apiService)
}

