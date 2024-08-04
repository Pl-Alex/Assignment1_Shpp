package com.alexP.assignment1.ui.authActivity

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexP.assignment1.utils.getValidationResultMessage
import com.alexp.datastore.data.DataStoreProvider
import com.alexp.textvalidation.data.TextValidation
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

data class TextValidationResult(
    val isSuccess: Boolean,
    @StringRes val message: Int
)

class AuthViewModel(
    private val dataStore: DataStoreProvider
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState get() = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.update {
                it.copy(
                    navEmail = dataStore.readEmail(),
                    isAutologin = dataStore.readRememberMeState()
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

    fun validateEmail(email: String): TextValidationResult {
        val validationResult = TextValidation().validateEmail(email)
        return TextValidationResult(
            validationResult == ValidationResult.SUCCESS,
            getValidationResultMessage(validationResult)
        )
    }

    fun validatePassword(password: String): TextValidationResult {
        val validationResult = TextValidation().validatePassword(password)
        return TextValidationResult(
            validationResult == ValidationResult.SUCCESS,
            getValidationResultMessage(validationResult)
        )
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

class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            AuthViewModel::class.java -> {
                val dataStore = DataStoreProvider(context)
                AuthViewModel(dataStore)
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}