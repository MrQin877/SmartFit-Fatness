package com.example.smartfit.data.repository

import com.example.smartfit.data.remote.ApiService
import com.example.smartfit.data.remote.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TipsRepository(private val api: ApiService) {
    suspend fun searchMeals(query: String): List<Meal> = withContext(Dispatchers.IO) {
        val resp = api.searchMeals(query)
        resp.meals ?: emptyList()
    }
}
