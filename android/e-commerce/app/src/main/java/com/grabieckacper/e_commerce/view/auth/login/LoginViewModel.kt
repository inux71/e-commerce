package com.grabieckacper.e_commerce.view.auth.login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.grabieckacper.e_commerce.R
import com.grabieckacper.e_commerce.common.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    data class LoginState(
        val email: String = "",
        val emailSupportingText: TextResource = TextResource.Empty,
        val emailError: Boolean = false,
        val password: String = "",
        val passwordVisible: Boolean = false,
        val passwordSupportingText: TextResource = TextResource.Empty,
        val passwordError: Boolean = false,
        val signedIn: Boolean = false
    )

    private val _state = mutableStateOf(value = LoginState())
    val state: State<LoginState>
        get() = _state

    private fun validateEmail() {
        if (!Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()) {
            _state.value = _state.value.copy(
                emailSupportingText = TextResource.StringResource(id = R.string.invalid_email),
                emailError = true
            )
        } else {
            _state.value = _state.value.copy(
                emailSupportingText = TextResource.Empty,
                emailError = false
            )
        }
    }

    private fun validatePassword() {
        if (_state.value.password.length < 8) {
            _state.value = _state.value.copy(
                passwordSupportingText = TextResource.StringResource(id = R.string.invalid_password),
                passwordError = true
            )
        } else {
            _state.value = _state.value.copy(
                passwordSupportingText = TextResource.Empty,
                passwordError = false
            )
        }
    }

    fun updateEmail(value: String) {
        _state.value = _state.value.copy(email = value)

        validateEmail()
    }

    fun clearEmail() {
        _state.value = _state.value.copy(
            email = "",
            emailSupportingText = TextResource.Empty,
            emailError = false
        )
    }

    fun updatePassword(value: String) {
        _state.value = _state.value.copy(password = value.trim())

        validatePassword()
    }

    fun updatePasswordVisibility() {
        _state.value = _state.value.copy(passwordVisible = !_state.value.passwordVisible)
    }

    fun signIn() {
        validateEmail()
        validatePassword()

        if (_state.value.emailError || _state.value.passwordError) {
            return
        } else {
            // TODO: sign in logic
            _state.value = _state.value.copy(signedIn = true)
        }
    }
}
