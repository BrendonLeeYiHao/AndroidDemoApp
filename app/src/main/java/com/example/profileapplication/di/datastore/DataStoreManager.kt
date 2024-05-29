package com.example.profileapplication.di.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val NAME = stringPreferencesKey("name")
}

class DataStoreManager(private val context: Context) {

    suspend fun saveUsername(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NAME] = name
        }
    }

    suspend fun getUsername(): String? {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.NAME]
            }
            .first()
    }

    suspend fun clearUsername() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.NAME)
        }
    }
}