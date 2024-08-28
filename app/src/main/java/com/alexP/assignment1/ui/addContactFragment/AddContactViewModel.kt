package com.alexP.assignment1.ui.addContactFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.alexp.textvalidation.data.validateAddress
import com.alexp.textvalidation.data.validateCareer
import com.alexp.textvalidation.data.validateDateOfBirth
import com.alexp.textvalidation.data.validateEmail
import com.alexp.textvalidation.data.validatePhone
import com.alexp.textvalidation.data.validateUsername
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddContactViewModel : ViewModel() {
    private val _galleryUri = MutableStateFlow<Uri?>(null)
    val galleryUri get() = _galleryUri.asStateFlow()

    fun setGalleryUri(uri: Uri) {
        _galleryUri.value = uri
    }

    fun validateUsernameVm(userName: String): ValidationResult {
        return validateUsername(userName)
    }

    fun validateCareerVm(career: String): ValidationResult {
        return validateCareer(career)
    }

    fun validateEmailVm(email: String): ValidationResult {
        return validateEmail(email)
    }

    fun validatePhoneVm(phone: String): ValidationResult {
        return validatePhone(phone)
    }

    fun validateAddressVm(address: String): ValidationResult {
        return validateAddress(address)
    }

    fun validateDateOfBirthVm(dateOdBirth: String): ValidationResult {
        return validateDateOfBirth(dateOdBirth)
    }

}