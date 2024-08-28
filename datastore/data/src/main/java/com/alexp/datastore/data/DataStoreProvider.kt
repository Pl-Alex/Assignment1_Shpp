package com.alexp.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private const val EMAIL = "email"
private const val REMEMBER_STATE = "remember_state"

private const val DATASTORE_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

class DataStoreProvider(private val context: Context) {

    private suspend fun saveString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    suspend fun saveCredentials(email: String) {
        saveRememberMeState(true)
        saveString(EMAIL, email)
    }

    private suspend fun saveBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        context.dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    private suspend fun readString(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey] ?: ""
    }

    private suspend fun readBoolean(key: String): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey] ?: false
    }

    suspend fun readRememberMeState(): Boolean {
        return readBoolean(REMEMBER_STATE)
    }

    suspend fun readEmail(): String {
        return readString(EMAIL)
    }

    suspend fun saveRememberMeState(isRememberMeChecked: Boolean) {
        saveBoolean(REMEMBER_STATE, isRememberMeChecked)
    }

    suspend fun cleanStorage() {
        context.dataStore.edit { pref ->
            pref.remove(stringPreferencesKey(EMAIL))
        }
    }

}
