package com.grabieckacper.e_commerce.view.auth.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.grabieckacper.e_commerce.R
import com.grabieckacper.e_commerce.common.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    data class RegisterState(
        val firstname: String = "",
        val firstnameSupportingText: TextResource = TextResource.Empty,
        val firstnameError: Boolean = false,
        val lastname: String = "",
        val lastnameSupportingText: TextResource = TextResource.Empty,
        val lastnameError: Boolean = false,
        val email: String = "",
        val emailSupportingText: TextResource = TextResource.Empty,
        val emailError: Boolean = false,
        val password: String = "",
        val passwordVisible: Boolean = false,
        val passwordSupportingText: TextResource = TextResource.Empty,
        val passwordError: Boolean = false,
        val signedUp: Boolean = false
    )

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState>
        get() = _state

    private fun validateFirstname() {
        if (_state.value.firstname.isEmpty()) {
            _state.value = _state.value.copy(
                firstnameSupportingText = TextResource.StringResource(
                    id = R.string.invalid_firstname
                ),
                firstnameError = true
            )
        } else {
            _state.value = _state.value.copy(
                firstnameSupportingText = TextResource.Empty,
                firstnameError = false
            )
        }
    }

    private fun validateLastname() {
        if (_state.value.lastname.isEmpty()) {
            _state.value = _state.value.copy(
                lastnameSupportingText = TextResource.StringResource(
                    id = R.string.invalid_lastname
                ),
                lastnameError = true
            )
        } else {
            _state.value = _state.value.copy(
                lastnameSupportingText = TextResource.Empty,
                lastnameError = false
            )
        }
    }

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

    fun updateFirstname(value: String) {
        _state.value = _state.value.copy(firstname = value.trim())

        validateFirstname()
    }

    fun clearFirstname() {
        _state.value = _state.value.copy(
            firstname = "",
            firstnameSupportingText = TextResource.Empty,
            firstnameError = false
        )
    }

    fun updateLastname(value: String) {
        _state.value = _state.value.copy(lastname = value.trim())

        validateLastname()
    }

    fun clearLastname() {
        _state.value = _state.value.copy(
            lastname = "",
            lastnameSupportingText = TextResource.Empty,
            lastnameError = false
        )
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

    fun signUp() {
        validateFirstname()
        validateLastname()
        validateEmail()
        validatePassword()

        if (
            _state.value.firstnameError ||
            _state.value.lastnameError ||
            _state.value.emailError ||
            _state.value.passwordError
        ) {
            return
        } else {
            // TODO: sign up logic
            _state.value = _state.value.copy(signedUp = true)
        }
    }
}
