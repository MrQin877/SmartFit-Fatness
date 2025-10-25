package com.example.smartfit.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

// Minimal placeholder - replace endpoints with chosen API specifics
interface ApiService {
    @GET("api/json/v1/1/search.php")
    suspend fun searchMeals(@Query("s") query: String): MealSearchResponse
}

// Placeholder response models for TheMealDB (adjust fields to actual API)
data class MealSearchResponse(val meals: List<Meal>?)
data class Meal(
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?
)
