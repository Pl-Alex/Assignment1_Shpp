package com.alexP.assignment1

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.data.dataStore
import com.alexP.assignment1.data.readEmail
import com.alexP.assignment1.data.readPassword
import com.alexP.assignment1.data.readRememberState
import com.alexP.assignment1.data.saveEmail
import com.alexP.assignment1.data.savePassword
import com.alexP.assignment1.data.saveRememberState
import com.alexP.assignment1.databinding.ActivityAuthBinding
import kotlinx.coroutines.launch


class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private lateinit var viewModel: AuthViewModel

    override fun inflate(inflater: LayoutInflater): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        lifecycleScope.launch {
            if (readRememberState(dataStore))
                moveToMyProfileActivity()
        }

        setPreferencesValues()
        setListeners()
    }

    private fun setPreferencesValues() {
        lifecycleScope.launch {
            if (readRememberState(dataStore)) {
                binding?.inputEditTextEmail?.setText(readEmail(dataStore))
                binding?.inputEditTextPassword?.setText(readPassword(dataStore))
                binding?.checkBoxRemember?.isChecked = true
            }
        }
    }

    private fun setListeners() {
        binding?.buttonRegister?.setOnClickListener {
            onRegisterButtonPressed()
        }
    }

    private suspend fun saveUserData() {
        binding?.inputEditTextEmail?.let {
            saveEmail(dataStore, it.text.toString())
        }
        binding?.inputEditTextPassword?.let {
            savePassword(dataStore, it.text.toString())
        }
        binding?.checkBoxRemember?.let {
            saveRememberState(dataStore, it.isChecked)
        }
    }

    private fun onRegisterButtonPressed() {
        var isDataValid = true
        with(viewModel) {
            val emailText = binding?.inputEditTextEmail?.text.toString()
            binding?.inputLayoutEmail?.error =
                if (!isEmailValid(emailText)) {
                    isDataValid = false
                    getString(getEmailErrorText()!!)
                } else null

            val passwordText = binding?.inputEditTextPassword?.text.toString()
            binding?.inputLayoutPassword?.error =
                if (!isPasswordValid(passwordText)) {
                    isDataValid = false
                    getString(getPasswordErrorText()!!)
                } else null
        }
        if (isDataValid) {
            lifecycleScope.launch {
                saveUserData()
            }
            moveToMyProfileActivity()
        }
    }

    private fun moveToMyProfileActivity() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

}