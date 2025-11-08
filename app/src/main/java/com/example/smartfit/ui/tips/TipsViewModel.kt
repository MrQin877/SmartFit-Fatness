// ui/tips/TipsViewModel.kt
/*
package com.example.smartfit.ui.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.remote.Meal             // adjust to your model
import com.example.smartfit.data.repository.TipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TipsUiState(
    val loading: Boolean = true,
    val items: List<Meal> = emptyList(),
    val error: String? = null
)

class TipsViewModel(
    private val repo: TipsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TipsUiState())
    val state: StateFlow<TipsUiState> = _state

    init { refresh() }

    fun refresh() = viewModelScope.launch {
        _state.value = _state.value.copy(loading = true, error = null)
        runCatching { repo.fetchMeals() }                       // repo method name in your project
            .onSuccess { _state.value = TipsUiState(false, it, null) }
            .onFailure { _state.value = TipsUiState(false, emptyList(), it.message) }
    }

}
*/