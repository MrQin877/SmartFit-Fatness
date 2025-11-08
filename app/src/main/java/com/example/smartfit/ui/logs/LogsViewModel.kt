// ui/logs/LogsViewModel.kt
package com.example.smartfit.ui.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.model.ActivityLog
import com.example.smartfit.data.repository.ActivityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LogsUiState(val items: List<ActivityLog> = emptyList())

class LogsViewModel(
    private val repo: ActivityRepository
) : ViewModel() {

    val state: StateFlow<LogsUiState> =
        repo.getAllActivities() // Flow<List<ActivityLog>>
            .map { LogsUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LogsUiState())

    fun delete(item: ActivityLog) = viewModelScope.launch { repo.delete(item) }
}
