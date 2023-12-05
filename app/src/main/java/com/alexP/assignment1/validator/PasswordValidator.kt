package com.alexP.assignment1.validator

import com.alexP.assignment1.R
import com.alexP.assignment1.validator.base.BaseValidator
import com.alexP.assignment1.validator.base.ValidateResult


class PasswordValidator(val password: String) : BaseValidator() {
    private val minPasswordLength = 8
    private val maxPasswordLength = 24

    val allowedCharactersRegex = Regex("[A-Za-z\\d@#$%^&+=]+")
    val uppercaseRegex = Regex("[A-Z]")
    val lowercaseRegex = Regex("[a-z]")
    val digitRegex = Regex("\\d")
    val specialCharRegex = Regex("[@#$%^&+=]")

    val containsUppercase = uppercaseRegex.containsMatchIn(password)
    val containsLowercase = lowercaseRegex.containsMatchIn(password)
    val containsDigit = digitRegex.containsMatchIn(password)
    val containsSpecialChar = specialCharRegex.containsMatchIn(password)

    override fun validate(): ValidateResult {
        if (!password.matches(allowedCharactersRegex))
            return ValidateResult(false, R.string.text_validation_error_contain_invalid_characters)
        if (!(containsUppercase && containsLowercase && containsDigit && containsSpecialChar))
            return ValidateResult(false, R.string.text_validation_error_needs_specified_characters)
        if (password.length < minPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_min_pass_length)
        if (password.length > maxPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_max_pass_length)
        return ValidateResult(true, R.string.text_validation_success)
    }


}