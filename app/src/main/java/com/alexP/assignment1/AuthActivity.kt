package com.alexP.assignment1

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.ActivityAuthBinding
import kotlinx.coroutines.launch


class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
    private lateinit var viewModel: AuthViewModel

    override fun inflate(inflater: LayoutInflater): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setPreferencesValues()
        setListeners()
    }

    private fun setPreferencesValues() {
        lifecycleScope.launch {
            if (viewModel.readBoolean(dataStore, "remember_state") == true) {
                binding.inputEditTextEmail.setText(viewModel.readString(dataStore, "email"))
                binding.inputEditTextPassword.setText(viewModel.readString(dataStore, "password"))
                binding.checkBoxRemember.isChecked = true
            }
        }
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {
            onRegisterButtonPressed()
        }
    }

    private fun onRegisterButtonPressed() {
        val emailValidationError = viewModel.validateEmail(
            binding.inputEditTextEmail.text.toString()
        )
        if (emailValidationError != null) {
            binding.inputLayoutEmail.error =
                getString(emailValidationError)
        } else {
            binding.inputLayoutEmail.error = null
        }

        val passwordValidationError = viewModel.validatePassword(
            binding.inputEditTextPassword.text.toString()
        )
        if (passwordValidationError != null) {
            binding.inputLayoutPassword.error =
                getString(passwordValidationError)
        } else {
            binding.inputLayoutPassword.error = null
        }

        if (emailValidationError == null && passwordValidationError == null) {
            lifecycleScope.launch {
                viewModel.saveString(
                    dataStore, "email",
                    binding.inputEditTextEmail.text.toString()
                )
                viewModel.saveString(
                    dataStore, "password",
                    binding.inputEditTextPassword.text.toString()
                )
                viewModel.saveBoolean(
                    dataStore, "remember_state",
                    binding.checkBoxRemember.isChecked
                )
            }

            val intent = Intent(this, MyProfileActivity::class.java)
            intent.putExtra(
                "email", viewModel.parseEmail(
                    binding.inputEditTextEmail.text.toString()
                )
            )
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
    }

}