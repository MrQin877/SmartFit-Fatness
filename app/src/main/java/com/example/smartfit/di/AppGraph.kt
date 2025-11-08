package com.example.smartfit.di

import android.content.Context
import com.example.smartfit.data.local.SmartFitDatabase
import com.example.smartfit.data.remote.ApiService
import com.example.smartfit.data.repository.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppGraph(context: Context) {
    private val db = SmartFitDatabase.create(context)

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/") // sample API
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create(ApiService::class.java)

    // Repositories
    val activityRepo = ActivityRepository(db.activityDao())
    val userRepo = UserRepository(db.userDao())
    val tipsRepo = TipsRepository(api)
    val prefsRepo = PrefsRepository(context.applicationContext)
}
