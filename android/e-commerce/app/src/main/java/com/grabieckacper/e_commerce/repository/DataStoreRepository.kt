package com.grabieckacper.e_commerce.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> write(key: Preferences.Key<T>, value: T)
}
