package com.alexP.assignment1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


const val EMAIL = "email"
const val PASSWORD = "password"
const val REMEMBER_STATE = "remember_state"

const val DATASTORE_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

suspend fun saveString(dataStore: DataStore<Preferences>, key: String, value: String) {
    val dataStoreKey = stringPreferencesKey(key)
    dataStore.edit { pref ->
        pref[dataStoreKey] = value
    }
}

suspend fun saveBoolean(dataStore: DataStore<Preferences>, key: String, value: Boolean) {
    val dataStoreKey = booleanPreferencesKey(key)
    dataStore.edit { pref ->
        pref[dataStoreKey] = value
    }
}

suspend fun readString(dataStore: DataStore<Preferences>, key: String): String? {
    val dataStoreKey = stringPreferencesKey(key)
    val preferences = dataStore.data.first()
    return preferences[dataStoreKey]
}

suspend fun readBoolean(dataStore: DataStore<Preferences>, key: String): Boolean? {
    val dataStoreKey = booleanPreferencesKey(key)
    val preferences = dataStore.data.first()
    return preferences[dataStoreKey]
}

suspend fun readRememberState(dataStore: DataStore<Preferences>): Boolean {
    return readBoolean(dataStore, REMEMBER_STATE) == true
}

suspend fun readEmail(dataStore: DataStore<Preferences>): String? {
    return readString(dataStore, EMAIL)
}

suspend fun readPassword(dataStore: DataStore<Preferences>): String? {
    return readString(dataStore, PASSWORD)
}

suspend fun saveEmail(dataStore: DataStore<Preferences>, email: String) {
    saveString(dataStore, EMAIL, email)
}

suspend fun savePassword(dataStore: DataStore<Preferences>, password: String) {
    saveString(dataStore, PASSWORD, password)
}

suspend fun saveRememberState(dataStore: DataStore<Preferences>, rememberState: Boolean) {
    saveBoolean(dataStore, REMEMBER_STATE, rememberState)
}



