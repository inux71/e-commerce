package com.grabieckacper.e_commerce.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun <T> read(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    override suspend fun <T> write(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}
