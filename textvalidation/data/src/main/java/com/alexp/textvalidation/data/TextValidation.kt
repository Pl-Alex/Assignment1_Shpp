package com.alexp.textvalidation.data

import com.alexp.textvalidation.data.validator.EmailValidator
import com.alexp.textvalidation.data.validator.EmptyValidator
import com.alexp.textvalidation.data.validator.PasswordValidator
import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult


fun validateEmail(email: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(email), EmailValidator(email))
}

fun validatePassword(password: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(password), PasswordValidator(password))
}

fun validateUsername(username: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(username))
}

fun validatePhone(phone: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(phone))
}

fun validateCareer(career: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(career))
}

fun validateAddress(address: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(address))
}

fun validateDateOfBirth(dateOfBirth: String): ValidationResult {
    return BaseValidator.validate(EmptyValidator(dateOfBirth))
}



