package com.alexP.assignment1.utils.validator.base

import androidx.annotation.StringRes

data class ValidateResult(
    val isSuccess: Boolean,
    @StringRes val message: Int
)
