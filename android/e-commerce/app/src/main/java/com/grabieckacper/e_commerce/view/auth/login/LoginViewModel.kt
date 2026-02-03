package com.grabieckacper.e_commerce.view.auth.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.e_commerce.R
import com.grabieckacper.e_commerce.common.TextResource
import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.model.Token
import com.grabieckacper.e_commerce.network.request.LoginRequest
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import com.grabieckacper.e_commerce.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class LoginState(
        val isLoading: Boolean = false,
        val error: TextResource = TextResource.Empty,
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
        viewModelScope.launch {
            validateEmail()
            validatePassword()

            if (_state.value.emailError || _state.value.passwordError) {
                return@launch
            }

            try {
                _state.value = _state.value.copy(isLoading = true)

                val loginRequest = LoginRequest(
                    email = _state.value.email,
                    password = _state.value.password
                )

                val token: Token = authService.signIn(loginRequest = loginRequest)

                dataStoreRepository.write(key = DataStoreKey.accessToken, value = token.token)

                _state.value = _state.value.copy(signedIn = true)
            } catch (e: RedirectResponseException) {
                Log.e("[RedirectResponseException]", e.message)

                _state.value = _state.value.copy(
                    error = TextResource.Text(text = e.message)
                )
            } catch (e: ClientRequestException) {
                Log.e("[ClientRequestException]", e.message)

                _state.value = _state.value.copy(
                    error = TextResource.Text(text = e.message)
                )
            } catch (e: ServerResponseException) {
                Log.e("[ServerResponseException]", e.message)

                _state.value = _state.value.copy(
                    error = TextResource.Text(text = e.message)
                )
            } catch (e: Exception) {
                Log.e("[Exception]", e.message ?: "")

                _state.value = _state.value.copy(
                    error = TextResource.Text(text = e.message ?: "")
                )
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
