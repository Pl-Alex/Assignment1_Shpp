package com.alexP.assignment1.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.alexP.assignment1.R
import com.alexp.textvalidation.data.validator.base.ValidationResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.Locale

fun ImageView.loadCircularImage(imageLink: String) {
    Glide.with(this).load(imageLink).apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.default_contact_image).error(R.drawable.default_contact_image)
        .into(this)
}

fun View.applyWindowInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(
            left = bars.left,
            top = bars.top,
            right = bars.right,
            bottom = bars.bottom,
        )
        WindowInsetsCompat.CONSUMED
    }
}

fun parseEmail(email: String): String {
    val namePart = email.substringBefore('@')

    return namePart.split('.')
        .joinToString(" ") { it ->
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        }
}

fun getValidationResultMessage(validationResult: ValidationResult): Int {
    return when (validationResult) {
        ValidationResult.SUCCESS -> R.string.text_validation_success
        ValidationResult.EMPTY_FAILED -> R.string.text_validation_error_empty_field
        ValidationResult.EMAIL_FAILED -> R.string.text_validation_error_email
        ValidationResult.INVALID_CHARACTERS_FAILED -> R.string.text_validation_error_contain_invalid_characters
        ValidationResult.NO_SPECIAL_CHARACTERS_FAILED -> R.string.text_validation_error_needs_specified_characters
        ValidationResult.MIN_LENGTH_FAILED -> R.string.text_validation_error_min_pass_length
        ValidationResult.MAX_LENGTH_FAILED -> R.string.text_validation_error_max_pass_length
    }
}