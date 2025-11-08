// ui/profile/ProfileViewModel.kt
package com.example.smartfit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.repository.PrefsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val prefs: PrefsRepository
) : ViewModel() {

    val themeMode: StateFlow<String> =
        prefs.themeMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "SYSTEM")

    val stepGoal: StateFlow<Int> =
        prefs.stepGoal().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 8000)

    fun setTheme(mode: String) = viewModelScope.launch { prefs.setThemeMode(mode) }
    fun setStepGoal(goal: Int) = viewModelScope.launch { prefs.setStepGoal(goal) }
    fun logout()               = viewModelScope.launch { prefs.setLoggedIn(false) }
}
