// app/src/main/java/com/example/smartfit/ui/home/HomeViewModel.kt
package com.example.smartfit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ActivityPeriod { DAILY, WEEKLY }
enum class ActivityFilter { ALL, EXERCISE, FOOD }
enum class ActivityType { EXERCISE, FOOD }
enum class ActivityIcon { RUNNING, CYCLING, FOOD, DRINK }

data class ActivityItemUiState(
    val id: Long,
    val type: ActivityType,
    val title: String,
    val subtitle: String,
    val caloriesText: String,
    val icon: ActivityIcon
)

data class ActivitySummaryUiState(
    val steps: Int,
    val activeMinutes: Int,
    val caloriesIntake: Int,
    val caloriesBurned: Int,
    val period: ActivityPeriod
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val summary: ActivitySummaryUiState = ActivitySummaryUiState(
        steps = 0,
        activeMinutes = 0,
        caloriesIntake = 0,
        caloriesBurned = 0,
        period = ActivityPeriod.DAILY
    ),
    val filter: ActivityFilter = ActivityFilter.ALL,
    val activities: List<ActivityItemUiState> = emptyList(),
    val tips: String = ""
)

class HomeViewModel : ViewModel() {   // ⬅️ 没有参数

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadDemoData()
    }

    private fun loadDemoData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val demoActivities = listOf(
                ActivityItemUiState(
                    id = 1,
                    type = ActivityType.EXERCISE,
                    title = "Running · 30 min",
                    subtitle = "4,000 steps · 220 kcal burned",
                    caloriesText = "-220 kcal",
                    icon = ActivityIcon.RUNNING
                ),
                ActivityItemUiState(
                    id = 2,
                    type = ActivityType.FOOD,
                    title = "Lunch · Chicken rice",
                    subtitle = "650 kcal intake",
                    caloriesText = "+650 kcal",
                    icon = ActivityIcon.FOOD
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    summary = ActivitySummaryUiState(
                        steps = 7560,
                        activeMinutes = 45,
                        caloriesIntake = 1850,
                        caloriesBurned = 620,
                        period = ActivityPeriod.DAILY
                    ),
                    activities = demoActivities,
                    tips = "Staying hydrated helps increase your metabolic rate and burn more calories."
                )
            }
        }
    }

    fun onPeriodChange(period: ActivityPeriod) {
        _uiState.update { state ->
            state.copy(summary = state.summary.copy(period = period))
        }
    }

    fun onFilterChange(filter: ActivityFilter) {
        _uiState.update { it.copy(filter = filter) }
    }
}
