package com.alexp.textvalidation.data

import com.alexp.textvalidation.data.validator.EmailValidator
import com.alexp.textvalidation.data.validator.EmptyValidator
import com.alexp.textvalidation.data.validator.PasswordValidator
import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult

class TextValidation {

    fun validateEmail(email: String): ValidationResult {
        return BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
    }
}