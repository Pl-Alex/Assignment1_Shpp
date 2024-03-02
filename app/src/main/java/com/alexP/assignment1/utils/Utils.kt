package com.alexP.assignment1.utils

import android.widget.ImageView
import com.alexP.assignment1.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadCircularImage(imageLink: String) {
    Glide.with(this).load(imageLink).apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.default_contact_image).error(R.drawable.default_contact_image)
        .into(this)
}