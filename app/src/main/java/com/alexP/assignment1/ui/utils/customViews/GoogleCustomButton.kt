package com.alexP.assignment1.ui.utils.customViews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.GoogleCustomButtonBinding

class GoogleCustomButton(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: GoogleCustomButtonBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.google_custom_button, this, true)
        binding = GoogleCustomButtonBinding.bind(this)

        setPadding(context.resources.getDimensionPixelSize(R.dimen.google_custom_view_padding))

        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        setDefaultAttr()
    }

    private fun setDefaultAttr() {
        isClickable = true

        val rippleColor = ContextCompat.getColor(context, R.color.light_grey_1)
        val rippleDrawable = RippleDrawable(ColorStateList.valueOf(rippleColor), background, null)
        background = rippleDrawable
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.GoogleCustomButton, defStyleAttr, defStyleRes
        )

        val buttonText = typedArray.getResourceId(
            R.styleable.GoogleCustomButton_buttonText,
            R.style.TextAppearance_Assignment1_LabelMedium
        )
        binding.buttonText.setTextAppearance(buttonText)

        val textColor =
            typedArray.getColor(R.styleable.GoogleCustomButton_textColor, Color.BLACK)
        binding.buttonText.setTextColor(textColor)

        val cornerRadius = typedArray.getDimension(
            R.styleable.GoogleCustomButton_cornerRadius,
            context.resources.getDimension(R.dimen.google_custom_button_cornerRadius)
        )

        val backgroundColor = typedArray.getColor(
            R.styleable.GoogleCustomButton_backgroundColor,
            Color.WHITE
        )

        val drawable = ContextCompat.getDrawable(
            context,
            R.drawable.google_custom_view_background
        ) as GradientDrawable
        drawable.cornerRadius = cornerRadius
        drawable.setColor(backgroundColor)
        background = drawable
        typedArray.recycle()
    }
}