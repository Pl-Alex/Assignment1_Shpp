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


private val emailKey = stringPreferencesKey("email")
private val usernameKey = stringPreferencesKey("username")
private val passwordKey = stringPreferencesKey("password")

private const val DATASTORE_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

class DataStoreProvider(private val context: Context) {

    private suspend fun <T> writeValue(dataStoreKey: Preferences.Key<T>, value: T) {
        context.dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    suspend fun saveCredentials(email: String, password: String) {
        writeValue(emailKey, email)
        writeValue(passwordKey, password)
    }

    fun credentialsAreNotEmpty(): Flow<Boolean> {
        return combine(readString(emailKey), readString(passwordKey)){email, password ->
            email.isNotEmpty() && password.isNotEmpty()
        }
    }

    suspend fun saveUsername(username: String) {
        writeValue(usernameKey, username)
    }

    /* private suspend fun readString(dataStoreKey: Preferences.Key<String>): String {
         return context.dataStore.data.first()[dataStoreKey] ?: ""
     }*/

    private fun readString(dataStoreKey: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: ""
        }
    }

    fun readUsername(): Flow<String> {
        return readString(usernameKey)
    }

    suspend fun cleanStorage() {
        context.dataStore.edit { pref ->
            pref.remove(emailKey)
            pref.remove(passwordKey)
            pref.remove(usernameKey)
        }
    }

}