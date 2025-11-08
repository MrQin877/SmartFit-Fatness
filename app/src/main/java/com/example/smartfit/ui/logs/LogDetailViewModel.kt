package com.example.smartfit.ui.logs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.model.ActivityLog
import com.example.smartfit.data.repository.ActivityRepository
import kotlinx.coroutines.flow.*

class LogDetailViewModel(
    repo: ActivityRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Long = checkNotNull(savedStateHandle["id"])

    val log: StateFlow<ActivityLog?> =
        repo.getById(id)                    // Flow<ActivityLog?>
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
