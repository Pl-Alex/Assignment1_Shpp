package com.alexp.textvalidation.data.validator.base

interface IValidator {
    fun validate(): ValidationResult
}

enum class ValidationResult {
    SUCCESS,
    EMPTY_FAILED,
    EMAIL_FAILED,
    INVALID_CHARACTERS_FAILED,
    NO_SPECIAL_CHARACTERS_FAILED,
    MIN_LENGTH_FAILED,
    MAX_LENGTH_FAILED,
}