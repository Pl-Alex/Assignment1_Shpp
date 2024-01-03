package com.alexP.assignment1

import androidx.lifecycle.ViewModel
import com.alexP.assignment1.validator.EmailValidator
import com.alexP.assignment1.validator.EmptyValidator
import com.alexP.assignment1.validator.PasswordValidator
import com.alexP.assignment1.validator.base.BaseValidator

class AuthViewModel : ViewModel() {

    fun validateEmail(email: String): Int? {
        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        return if (!emailValidations.isSuccess) emailValidations.message else null
    }

    fun validatePassword(password: String): Int? {
        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        return if (!passwordValidations.isSuccess) passwordValidations.message else null
    }


}