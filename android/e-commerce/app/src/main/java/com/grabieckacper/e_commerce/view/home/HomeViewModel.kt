package com.grabieckacper.e_commerce.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    fun signOut() {
        viewModelScope.launch {
            dataStoreRepository.remove(key = DataStoreKey.accessToken)
        }
    }
}
