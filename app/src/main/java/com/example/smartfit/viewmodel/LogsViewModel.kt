package com.example.smartfit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.model.ActivityLog
import com.example.smartfit.data.repository.ActivityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogsViewModel(private val repo: ActivityRepository) : ViewModel() {
    val logs: StateFlow<List<ActivityLog>> = repo.getAllActivities()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun delete(id: Long) = viewModelScope.launch {
        repo.getById(id) // could load then delete; placeholder:
    }
}
