package com.alexp.textvalidation.validator

import com.alexp.textvalidation.validator.base.BaseValidator
import com.alexp.textvalidation.validator.base.ValidationResult
import com.alexp.textvalidation.validator.base.ValidationResult.INVALID_CHARACTERS_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.MAX_LENGTH_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.MIN_LENGTH_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.NO_SPECIAL_CHARACTERS_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.SUCCESS


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

    override fun validate(): ValidationResult {
        return when {
            !password.matches(allowedCharactersRegex) ->
                INVALID_CHARACTERS_FAILED

            !(containsUppercase && containsLowercase && containsDigit && containsSpecialChar) ->
                NO_SPECIAL_CHARACTERS_FAILED

            password.length < minPasswordLength ->
                MIN_LENGTH_FAILED

            password.length > maxPasswordLength ->
                MAX_LENGTH_FAILED

            else ->
                return SUCCESS
        }
    }
}