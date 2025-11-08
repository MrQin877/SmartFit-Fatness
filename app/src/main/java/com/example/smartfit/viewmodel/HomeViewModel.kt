package com.example.smartfit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.repository.ActivityRepository
import com.example.smartfit.data.model.ActivityLog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val activityRepo: ActivityRepository) : ViewModel() {
    val todaySummary: StateFlow<Int> = activityRepo.getAllActivities()
        .map { logs -> logs.sumOf { it.steps ?: 0 } } // simple example: sum steps
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun refresh() {
        viewModelScope.launch {
            // trigger recompute or fetch if needed
        }
    }
}
