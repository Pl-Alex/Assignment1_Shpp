package com.alexP.assignment1.ui.myProfileActivity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.ActivityMyProfileBinding
import com.alexP.assignment1.ui.BaseActivity
import com.alexP.assignment1.ui.authActivity.AuthActivity
import com.alexP.assignment1.utils.parseEmail
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class MyProfileActivity : BaseActivity<ActivityMyProfileBinding>() {

    private lateinit var viewModel: MyProfileViewModel

    companion object {
        const val IMAGE_LINK =
            "https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640"
    }

    override fun inflate(inflater: LayoutInflater): ActivityMyProfileBinding {
        return ActivityMyProfileBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, MyProfileViewModelFactory(this))[MyProfileViewModel::class.java]

        binding.let {
            Glide.with(this)
                .load(IMAGE_LINK)
                .into(it.imageViewProfileImage)
        }

        setListeners()
        lifecycleScope.launch {
            viewModel.emailState.collect { email ->
                binding.textViewNameSurname.text = parseEmail(email)
            }
        }
    }

    private fun setListeners() {
        binding.buttonLogOut.setOnClickListener {
            onLogOutButtonPressed()
        }
    }

    private fun onLogOutButtonPressed() {
        viewModel.cleanStorage()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }
}