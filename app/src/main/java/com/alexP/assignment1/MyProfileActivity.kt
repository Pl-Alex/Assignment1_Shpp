package com.alexP.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.ActivityAuthBinding
import com.alexP.assignment1.databinding.ActivityMyProfileBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class MyProfileActivity : BaseActivity<ActivityMyProfileBinding>() {

    override fun inflate(inflater: LayoutInflater): ActivityMyProfileBinding {
        return ActivityMyProfileBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra("email")
        binding.textViewNameSurname.text = email

        Glide.with(this)
            .load("https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640")
            .into(binding.imageViewProfileImage)
    }
}