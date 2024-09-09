package com.alexp.textvalidation.validator

import com.alexp.textvalidation.validator.base.BaseValidator
import com.alexp.textvalidation.validator.base.ValidationResult
import com.alexp.textvalidation.validator.base.ValidationResult.EMAIL_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.SUCCESS
import java.util.regex.Pattern


class EmailValidator(private val email: String) : BaseValidator() {
    override fun validate(): ValidationResult {
        val patternEmailAddress = Pattern.compile(
            ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")
        );

        return if (patternEmailAddress.matcher(email).matches())
            SUCCESS else EMAIL_FAILED
    }
}