package com.alexP.assignment1.utils.validator

import com.alexP.assignment1.R
import com.alexP.assignment1.utils.validator.base.BaseValidator
import com.alexP.assignment1.utils.validator.base.ValidateResult

class EmptyValidator(private val input: String) : BaseValidator(){
    override fun validate(): ValidateResult {
        val isValid = input.isNotEmpty()
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success
            else R.string.text_validation_error_empty_field
        )
    }

}