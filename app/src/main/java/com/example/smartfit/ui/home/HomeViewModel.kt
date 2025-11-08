// ui/home/HomeViewModel.kt
package com.example.smartfit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.repository.ActivityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

data class HomeUiState(
    val stepsToday: Int = 0,
    val caloriesToday: Int = 0,
    val workouts: List<String> = emptyList()
)

class HomeViewModel(
    repo: ActivityRepository
) : ViewModel() {

    val ui: StateFlow<HomeUiState> =
        repo.getAllActivities()
            .map { logs ->
                val today = LocalDate.now().toString()
                val todayLogs = logs.filter { it.date == today }
                HomeUiState(
                    stepsToday = todayLogs.sumOf { it.steps ?: 0 },
                    caloriesToday = todayLogs.sumOf { it.calories ?: 0 },
                    workouts = todayLogs.mapNotNull { it.type }
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())
}
