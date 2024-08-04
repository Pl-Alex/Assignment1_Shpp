package com.alexp.textvalidation.data.validator

import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult
import com.alexp.textvalidation.data.validator.base.ValidationResult.EMPTY_FAILED
import com.alexp.textvalidation.data.validator.base.ValidationResult.SUCCESS

class EmptyValidator(private val input: String) : BaseValidator(){
    override fun validate(): ValidationResult {
        return if(input.isNotEmpty())
            SUCCESS else EMPTY_FAILED
    }
}