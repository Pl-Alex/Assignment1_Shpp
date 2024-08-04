package com.alexP.assignment1.ui.addContactFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.alexP.assignment1.utils.getValidationResultMessage
import com.alexp.textvalidation.data.validator.EmailValidator
import com.alexp.textvalidation.data.validator.EmptyValidator
import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddContactViewModel : ViewModel() {
    private val _galleryUri = MutableStateFlow<Uri?>(null)
    val galleryUri get() = _galleryUri.asStateFlow()

    fun setGalleryUri(uri: Uri) {
        _galleryUri.value = uri
    }

    private fun emptyValidation(text: String): Int? {
        val result = BaseValidator.validate(
            EmptyValidator(
                text
            )
        )
        return if (result == ValidationResult.SUCCESS) {
            null
        } else {
            getValidationResultMessage(result)
        }
    }

    fun validateUsername(username: String): Int? {
        return emptyValidation(username)
    }

    fun validatePhone(phone: String): Int? {
        return emptyValidation(phone)
    }

    fun validateCareer(career: String): Int? {
        return emptyValidation(career)
    }

    fun validateEmail(email: String): Int? {
        val result = BaseValidator.validate(
            EmptyValidator(
                email
            ), EmailValidator(email)
        )
        return if (result == ValidationResult.SUCCESS) {
            null
        } else {
            getValidationResultMessage(result)
        }
    }

    fun validateAddress(address: String): Int? {
        return emptyValidation(address)
    }

    fun validateDateOfBirth(dateOfBirth: String): Int? {
        return emptyValidation(dateOfBirth)
    }
}