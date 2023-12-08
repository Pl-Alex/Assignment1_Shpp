package com.alexP.assignment1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.alexP.assignment1.databinding.AuthActivityBinding
import com.alexP.assignment1.databinding.MyProfileActivityBinding
import com.bumptech.glide.Glide

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: MyProfileActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        binding.textViewNameSurname.text = email

        Glide.with(this)
            .load("https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640")
            .into(binding.imageViewProfileImage)
    }
}