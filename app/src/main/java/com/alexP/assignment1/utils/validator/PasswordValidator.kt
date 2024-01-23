package com.alexP.assignment1.utils.validator

import com.alexP.assignment1.R
import com.alexP.assignment1.utils.validator.base.BaseValidator
import com.alexP.assignment1.utils.validator.base.ValidateResult


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
        return when {
            !password.matches(allowedCharactersRegex) ->
                ValidateResult(false, R.string.text_validation_error_contain_invalid_characters)

            !(containsUppercase && containsLowercase && containsDigit && containsSpecialChar) ->
                ValidateResult(false, R.string.text_validation_error_needs_specified_characters)

            password.length < minPasswordLength ->
                ValidateResult(false, R.string.text_validation_error_min_pass_length)

            password.length > maxPasswordLength ->
                ValidateResult(false, R.string.text_validation_error_max_pass_length)

            else ->
                ValidateResult(true, R.string.text_validation_success)
        }
    }
}