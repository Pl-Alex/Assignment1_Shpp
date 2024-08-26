package com.alexP.assignment1.ui.authActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class AuthViewModel(
    private val dataStore: DataStoreProvider,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState get() = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.update {
                it.copy(
                    navEmail = dataStore.readEmail(),
                    isAutologin = dataStore.readRememberMeState()
                            && dataStore.readEmail().isNotEmpty()
                )
            }
        }
    }

    fun saveAutologin(email: String, password: String, isRememberMeChecked: Boolean?) {
        if (isRememberMeChecked == true) {
            viewModelScope.launch {
                dataStore.saveCredentials(email, password)
            }
        }
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