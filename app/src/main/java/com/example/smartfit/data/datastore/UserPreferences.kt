package com.example.smartfit.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.content.Context


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private val KEY_ONBOARDED = booleanPreferencesKey("is_onboarded")
    private val KEY_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    fun getOnboarded(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[KEY_ONBOARDED] ?: false
        }

    suspend fun setOnboarded(context: Context, value: Boolean) =
        context.dataStore.edit { prefs ->
            prefs[KEY_ONBOARDED] = value
        }

    fun getLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[KEY_LOGGED_IN] ?: false
        }

    suspend fun setLoggedIn(context: Context, value: Boolean) =
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = value
        }



    private val KEY_STEP_GOAL = intPreferencesKey("daily_step_goal")
    private val KEY_THEME = stringPreferencesKey("theme_mode")

    fun getStepGoal(context: Context): Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[KEY_STEP_GOAL] ?: 8000
    }

    suspend fun setStepGoal(context: Context, value: Int) = context.dataStore.edit { prefs ->
        prefs[KEY_STEP_GOAL] = value
    }

    fun getTheme(context: Context): Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_THEME] ?: "SYSTEM"
    }

    suspend fun setTheme(context: Context, value: String) = context.dataStore.edit { prefs ->
        prefs[KEY_THEME] = value
    }
}
