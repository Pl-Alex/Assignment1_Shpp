package com.alexP.assignment1.ui.authActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.datastore.data.DataStoreProvider
import com.alexp.textvalidation.data.validateEmail
import com.alexp.textvalidation.data.validatePassword
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val dataStore: DataStoreProvider,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState get() = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.update {
                it.copy(
                    username = dataStore.readUsername().first(),
                    isAutologin = dataStore.credentialsAreNotEmpty().first()
                )
            }
            dataStore.readUsername().collect { username ->
                _authState.update { it.copy(username = username) }
            }
            dataStore.credentialsAreNotEmpty().collect { credentialsAreNotEmpty ->
                _authState.update { it.copy(isAutologin = credentialsAreNotEmpty) }
            }
        }
    }

    fun saveCredentials(email: String, password: String) {
        viewModelScope.launch {
            dataStore.saveCredentials(email, password)
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            dataStore.saveUsername(username)
        }
    }

    fun validateEmailVm(email: String): ValidationResult {
        return validateEmail(email)
    }

    fun validatePasswordVm(password: String): ValidationResult {
        return validatePassword(password)
    }

    companion object {
        fun createFactory(
            dataStore: DataStoreProvider,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(dataStore)
            }
        }
    }
}