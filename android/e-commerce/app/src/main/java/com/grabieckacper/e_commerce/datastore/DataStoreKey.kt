package com.grabieckacper.e_commerce.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

object DataStoreKey {
    val darkTheme: Preferences.Key<Boolean> = booleanPreferencesKey(name = "dark-theme")
    val dynamicColor: Preferences.Key<Boolean> = booleanPreferencesKey(name = "dynamic-color")
}
