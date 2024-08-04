package com.alexp.textvalidation.data.validator.base

import com.alexp.textvalidation.data.validator.base.ValidationResult.SUCCESS


abstract class BaseValidator : IValidator {
    companion object {
        fun validate(vararg validators: IValidator): ValidationResult {
            validators.forEach {
                val result = it.validate()
                if (result != SUCCESS)
                    return result
            }
            return SUCCESS
        }
    }
}