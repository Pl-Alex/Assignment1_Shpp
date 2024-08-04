package com.alexp.textvalidation.data.validator

import android.util.Patterns
import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult
import com.alexp.textvalidation.data.validator.base.ValidationResult.EMAIL_FAILED
import com.alexp.textvalidation.data.validator.base.ValidationResult.SUCCESS

class EmailValidator(private val email: String) : BaseValidator() {
    override fun validate(): ValidationResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            SUCCESS else EMAIL_FAILED
    }
}