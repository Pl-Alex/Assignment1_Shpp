package com.alexP.socialnetwork.ui.screens.contacts

data class Contact(
    val id: Long,
    val photo: String,
    val fullName: String,
    val career: String,
    val email: String,
    val phone: String,
    val address: String,
    val dateOfBirth: String,
)