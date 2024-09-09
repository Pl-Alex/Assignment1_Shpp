package com.alexp.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


private const val emailKey = "email"
private const val passwordKey = "password"

private const val DATASTORE_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

class DataStoreProvider(private val context: Context) {

    private suspend fun <T> writeValue(dataStoreKey: Preferences.Key<T>, value: T) {
        context.dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    suspend fun saveCredentials(email: String, password: String) {
        writeValue(stringPreferencesKey(emailKey), email)
        writeValue(stringPreferencesKey(passwordKey), password)
    }

    fun getCredentials(): Flow<Pair<String, String>> {
        return combine(
            readString(stringPreferencesKey(emailKey)),
            readString(stringPreferencesKey(passwordKey))
        ) { email, password ->
            email to password
        }
    }

    private fun readString(dataStoreKey: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: ""
        }
    }

    fun readEmail(): Flow<String> {
        return readString(stringPreferencesKey(emailKey))
    }

    suspend fun cleanStorage() {
        context.dataStore.edit { pref ->
            pref.remove(stringPreferencesKey(emailKey))
            pref.remove(stringPreferencesKey(passwordKey))
        }
    }

}