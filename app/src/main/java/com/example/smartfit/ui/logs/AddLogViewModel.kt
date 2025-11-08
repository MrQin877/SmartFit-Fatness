// app/src/main/java/com/example/smartfit/ui/logs/AddLogViewModel.kt
package com.example.smartfit.ui.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.model.ActivityLog
import com.example.smartfit.data.repository.ActivityRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AddLogUiState(
    val title: String = "Evening Run",
    val type: String = "Running",
    val durationMin: String = "45",   // keep as text for the form
    val distanceKm: String = "8.50",  // keep as text for the form
    val calories: String = "430",
    val saving: Boolean = false,
    val error: String? = null
)

sealed interface AddLogEffect { data object Close : AddLogEffect }

class AddLogViewModel(
    private val repo: ActivityRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddLogUiState())
    val state: StateFlow<AddLogUiState> = _state

    private val _effect = Channel<AddLogEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun update(block: (AddLogUiState) -> AddLogUiState) {
        _state.value = block(_state.value.copy(error = null))
    }

    fun save() = viewModelScope.launch {
        val s = _state.value
        if (s.saving) return@launch

        if (s.title.isBlank() || s.type.isBlank()) {
            _state.value = s.copy(error = "Please fill activity name and type.")
            return@launch
        }

        val duration = s.durationMin.toIntOrNull() ?: 0
        val calories = s.calories.toIntOrNull() ?: 0
        val distance = s.distanceKm.toDoubleOrNull() ?: 0.0 // keep if you later add a column
        // steps aren’t in your form; the entity field is nullable so we can pass null

        _state.value = s.copy(saving = true)

        val log = ActivityLog(
            date = LocalDate.now().toString(),   // entity requires a String date
            type = s.type,
            durationMin = duration,              // 🔁 correct field name
            steps = null,
            calories = calories,
            notes = s.title                       // storing title in notes for now
            // sourceProvider/sourceId/sourceTitle/imageUrl left null
        )

        repo.insert(log)                          // 🔁 repository method name
        _effect.send(AddLogEffect.Close)
        _state.value = _state.value.copy(saving = false)
    }
}
