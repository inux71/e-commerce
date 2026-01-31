package com.grabieckacper.e_commerce.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class ThemeState(
        val darkTheme: Boolean = false,
        val dynamicColor: Boolean = false
    )

    private val _state = mutableStateOf(ThemeState())
    val state: State<ThemeState>
        get() = _state

    init {
        readDarkTheme()
        readDynamicColor()
    }

    private fun readDarkTheme() {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = DataStoreKey.darkTheme,
                defaultValue = false
            ).collect { value ->
                updateDarkTheme(value = value)
            }
        }
    }

    private fun readDynamicColor() {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = DataStoreKey.dynamicColor,
                defaultValue = false
            ).collect { value ->
                updateDynamicColor(value = value)
            }
        }
    }

    fun updateDarkTheme(value: Boolean) {
        _state.value = _state.value.copy(darkTheme = value)
    }

    fun updateDynamicColor(value: Boolean) {
        _state.value = _state.value.copy(dynamicColor = value)
    }
}
