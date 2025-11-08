package com.example.smartfit.data.repository

import android.content.Context
import com.example.smartfit.data.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

class PrefsRepository(private val appContext: Context) {
    // Onboarding
    fun isOnboarded(): Flow<Boolean> = UserPreferences.getOnboarded(appContext)
    suspend fun setOnboarded(v: Boolean) = UserPreferences.setOnboarded(appContext, v)

    // Auth
    fun isLoggedIn(): Flow<Boolean> = UserPreferences.getLoggedIn(appContext)
    suspend fun setLoggedIn(v: Boolean) = UserPreferences.setLoggedIn(appContext, v)

    // Theme: "SYSTEM" | "LIGHT" | "DARK"
    fun themeMode(): Flow<String> = UserPreferences.getTheme(appContext)
    suspend fun setThemeMode(mode: String) = UserPreferences.setTheme(appContext, mode)

    // Goals
    fun stepGoal(): Flow<Int> = UserPreferences.getStepGoal(appContext)
    suspend fun setStepGoal(goal: Int) = UserPreferences.setStepGoal(appContext, goal)
}
