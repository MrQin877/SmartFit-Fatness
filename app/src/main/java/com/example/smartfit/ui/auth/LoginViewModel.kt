// ui/auth/LoginViewModel.kt
package com.example.smartfit.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfit.data.repository.PrefsRepository
import com.example.smartfit.data.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

sealed interface LoginEffect { data object NavigateHome : LoginEffect }

class LoginViewModel(
    private val users: UserRepository,
    private val prefs: PrefsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEmail(v: String)    { _state.value = _state.value.copy(email = v,  error = null) }
    fun onPassword(v: String) { _state.value = _state.value.copy(password = v, error = null) }

    fun login() = viewModelScope.launch {
        val s = _state.value
        if (s.loading) return@launch
        _state.value = s.copy(loading = true, error = null)
        val ok = users.login(s.email, s.password) != null
        if (ok) {
            prefs.setLoggedIn(true)
            _effect.send(LoginEffect.NavigateHome)
        } else {
            _state.value = _state.value.copy(error = "Invalid email or password")
        }
        _state.value = _state.value.copy(loading = false)
    }
}
