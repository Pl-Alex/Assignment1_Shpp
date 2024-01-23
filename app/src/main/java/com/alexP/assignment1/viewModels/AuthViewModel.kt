package com.alexP.assignment1.viewModels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import com.alexP.assignment1.utils.validator.EmailValidator
import com.alexP.assignment1.utils.validator.EmptyValidator
import com.alexP.assignment1.utils.validator.PasswordValidator
import com.alexP.assignment1.utils.validator.base.BaseValidator
import kotlinx.coroutines.flow.first
import java.util.Locale

class AuthViewModel : ViewModel() {

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

    fun validateEmail(email: String) : Int?{
        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        return if (!emailValidations.isSuccess) emailValidations.message else null
    }

    fun validatePassword(password: String) : Int?{
        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        return if (!passwordValidations.isSuccess) passwordValidations.message else null
    }



    fun parseEmail(email: String): String {
        val namePart = email.substringBefore('@')

        return namePart.split('.')
            .joinToString(" ") { it ->
                it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
    }

}