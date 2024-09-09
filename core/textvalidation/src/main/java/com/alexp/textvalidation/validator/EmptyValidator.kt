package com.alexp.textvalidation.validator

import com.alexp.textvalidation.validator.base.BaseValidator
import com.alexp.textvalidation.validator.base.ValidationResult
import com.alexp.textvalidation.validator.base.ValidationResult.EMPTY_FAILED
import com.alexp.textvalidation.validator.base.ValidationResult.SUCCESS

class EmptyValidator(private val input: String) : BaseValidator() {
    override fun validate(): ValidationResult {
        return if (input.isNotEmpty())
            SUCCESS else EMPTY_FAILED
    }
}