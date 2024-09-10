package com.alexP.assignment1

import androidx.lifecycle.ViewModel
import com.alexP.assignment1.validator.EmailValidator
import com.alexP.assignment1.validator.EmptyValidator
import com.alexP.assignment1.validator.PasswordValidator
import com.alexP.assignment1.validator.base.BaseValidator

class AuthViewModel : ViewModel() {

    private var emailErrorText: Int? = null
    fun getEmailErrorText() = emailErrorText

    private var passwordErrorText: Int? = null
    fun getPasswordErrorText() = passwordErrorText

    fun isEmailValid(email: String): Boolean {
        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        emailErrorText = emailValidations.message
        return emailValidations.isSuccess
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        passwordErrorText = passwordValidations.message
        return passwordValidations.isSuccess
    }
}