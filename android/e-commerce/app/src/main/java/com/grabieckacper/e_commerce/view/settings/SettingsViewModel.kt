package com.grabieckacper.e_commerce.view.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class SettingsState(
        val darkTheme: Boolean = false,
        val dynamicColor: Boolean = false
    )

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState>
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

    private fun <T> updateDataStore(key: Preferences.Key<T>, value: T) {
        viewModelScope.launch {
            dataStoreRepository.write(
                key = key,
                value = value
            )
        }
    }

    fun updateDarkTheme(value: Boolean) {
        _state.value = _state.value.copy(darkTheme = value)

        updateDataStore(key = DataStoreKey.darkTheme, value = value)
    }

    fun updateDynamicColor(value: Boolean) {
        _state.value = _state.value.copy(dynamicColor = value)

        updateDataStore(key = DataStoreKey.dynamicColor, value = value)
    }
}
