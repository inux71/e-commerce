package com.grabieckacper.e_commerce.navigation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.model.Token
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import com.grabieckacper.e_commerce.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationGraphViewModel @Inject constructor(
    private val authService: AuthService,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class NavigationState(val signedIn: Boolean? = null)

    private val _state = mutableStateOf(value = NavigationState())
    val state: State<NavigationState>
        get() = _state

    init {
        checkIfSignedIn()
    }

    private fun checkIfSignedIn() {
        viewModelScope.launch {
            dataStoreRepository.read(key = DataStoreKey.accessToken, defaultValue = "")
                .collect { value ->
                    _state.value = _state.value.copy(signedIn = value.isNotEmpty())
                }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            try {
                val token: Token = authService.refresh()

                dataStoreRepository.write(key = DataStoreKey.accessToken, value = token.token)
            } catch (e: RedirectResponseException) {
                Log.e("[RedirectResponseException]", e.message)
            } catch (e: ClientRequestException) {
                Log.e("[ClientRequestException]", e.message)

                if (e.response.status == HttpStatusCode.Unauthorized) {
                    _state.value = _state.value.copy(signedIn = false)
                }
            } catch (e: ServerResponseException) {
                Log.e("[ServerResponseException]", e.message)
            } catch (e: Exception) {
                Log.e("[Exception]", e.message ?: "")
            }
        }
    }
}
