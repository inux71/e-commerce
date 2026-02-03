package com.grabieckacper.e_commerce.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKey {
    val accessToken: Preferences.Key<String> = stringPreferencesKey(name = "access-token")
    val darkTheme: Preferences.Key<Boolean> = booleanPreferencesKey(name = "dark-theme")
    val dynamicColor: Preferences.Key<Boolean> = booleanPreferencesKey(name = "dynamic-color")
}
