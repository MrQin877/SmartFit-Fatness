// app/src/main/java/com/example/smartfit/ui/auth/OnboardingViewModel.kt
package com.example.smartfit.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.repository.PrefsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val step: Int = 1,
    val goal: Int = 8000,   // store as Int; UI can edit as Float
    val loading: Boolean = false
)

sealed interface OnboardingEffect { data object Finished : OnboardingEffect }

class OnboardingViewModel(
    private val prefs: PrefsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state

    private val _effect = Channel<OnboardingEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun next() { _state.value = _state.value.copy(step = 2) }

    fun setGoal(value: Int) {
        _state.value = _state.value.copy(goal = value.coerceIn(1000, 20000))
    }

    fun skip() = finish(saveGoal = false)

    fun finish(saveGoal: Boolean = true) = viewModelScope.launch {
        _state.value = _state.value.copy(loading = true)
        if (saveGoal) prefs.setStepGoal(_state.value.goal)
        prefs.setOnboarded(true)
        _state.value = _state.value.copy(loading = false)
        _effect.send(OnboardingEffect.Finished)
    }
}
