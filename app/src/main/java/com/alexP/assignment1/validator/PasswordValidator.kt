package com.alexP.assignment1.validator

import com.alexP.assignment1.R
import com.alexP.assignment1.validator.base.BaseValidator
import com.alexP.assignment1.validator.base.ValidateResult


class PasswordValidator(private val password: String) : BaseValidator() {
    private val minPasswordLength = 8
    private val maxPasswordLength = 24

    private val allowedCharactersRegex = Regex("[A-Za-z\\d@#$%^&+=]+")
    private val uppercaseRegex = Regex("[A-Z]")
    private val lowercaseRegex = Regex("[a-z]")
    private val digitRegex = Regex("\\d")
    private val specialCharRegex = Regex("[@#$%^&+=]")

    private val containsUppercase = uppercaseRegex.containsMatchIn(password)
    private val containsLowercase = lowercaseRegex.containsMatchIn(password)
    private val containsDigit = digitRegex.containsMatchIn(password)
    private val containsSpecialChar = specialCharRegex.containsMatchIn(password)

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