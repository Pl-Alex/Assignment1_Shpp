package com.alexP.assignment1.utils.validator

import android.text.TextUtils
import android.util.Patterns
import com.alexP.assignment1.R
import com.alexP.assignment1.utils.validator.base.BaseValidator
import com.alexP.assignment1.utils.validator.base.ValidateResult

class EmailValidator(private val email: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid =
            !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success
            else R.string.text_validation_error_email
        )
    }
}