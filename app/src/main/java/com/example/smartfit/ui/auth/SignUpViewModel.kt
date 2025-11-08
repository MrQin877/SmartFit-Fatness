// ui/auth/SignUpViewModel.kt
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

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirm: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

sealed interface SignUpEffect { data object NavigateLogin : SignUpEffect }

class SignUpViewModel(
    private val users: UserRepository,
    private val prefs: PrefsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState> = _state

    private val _effect = Channel<SignUpEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onName(v: String)     { _state.value = _state.value.copy(name = v,  error = null) }
    fun onEmail(v: String)    { _state.value = _state.value.copy(email = v, error = null) }
    fun onPassword(v: String) { _state.value = _state.value.copy(password = v, error = null) }
    fun onConfirm(v: String)  { _state.value = _state.value.copy(confirm = v,  error = null) }

    fun signUp() = viewModelScope.launch {
        val s = _state.value
        if (s.loading) return@launch
        if (s.name.isBlank() || s.email.isBlank() || s.password.length < 6) {
            _state.value = s.copy(error = "Please fill name, valid email and 6+ char password.")
            return@launch
        }
        if (s.password != s.confirm) {
            _state.value = s.copy(error = "Passwords do not match.")
            return@launch
        }
        _state.value = s.copy(loading = true, error = null)

        val created = users.registerUser(s.name, s.email, s.password) // adjust to your API
        if (created != null) {
            prefs.setLoggedIn(false) // after sign-up, send to Login (or set true to go Home)
            _effect.send(SignUpEffect.NavigateLogin)
        } else {
            _state.value = _state.value.copy(error = "Sign up failed. Try again.")
        }
        _state.value = _state.value.copy(loading = false)
    }
}
