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

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    private lateinit var viewModel: AuthViewModel

    companion object {
        const val DATASTORE_NAME = "user_preferences"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val REMEMBER_STATE = "remember_state"
    }

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
            if (viewModel.readBoolean(dataStore, REMEMBER_STATE) == true) {
                binding?.inputEditTextEmail?.setText(viewModel.readString(dataStore, EMAIL))
                binding?.inputEditTextPassword?.setText(viewModel.readString(dataStore, PASSWORD))
                binding?.checkBoxRemember?.isChecked = true
            }
        }
    }

    private fun setListeners() {
        binding?.buttonRegister?.setOnClickListener {
            onRegisterButtonPressed()
        }
    }

    private fun onRegisterButtonPressed() {
        val emailValidationError = viewModel.validateEmail(
            binding?.inputEditTextEmail?.text.toString()
        )
        if (emailValidationError != null) {
            binding?.inputLayoutEmail?.error =
                getString(emailValidationError)
        } else {
            binding?.inputLayoutEmail?.error = null
        }

        val passwordValidationError = viewModel.validatePassword(
            binding?.inputEditTextPassword?.text.toString()
        )
        if (passwordValidationError != null) {
            binding?.inputLayoutPassword?.error =
                getString(passwordValidationError)
        } else {
            binding?.inputLayoutPassword?.error = null
        }

        if (emailValidationError == null && passwordValidationError == null) {
            lifecycleScope.launch {
                viewModel.saveString(
                    dataStore, EMAIL,
                    binding?.inputEditTextEmail?.text.toString()
                )
                viewModel.saveString(
                    dataStore, PASSWORD,
                    binding?.inputEditTextPassword?.text.toString()
                )
                binding?.checkBoxRemember?.let {
                    viewModel.saveBoolean(
                        dataStore, REMEMBER_STATE,
                        it.isChecked
                    )
                }
            }

            val intent = Intent(this, MyProfileActivity::class.java)
            intent.putExtra(
                EMAIL, viewModel.parseEmail(
                    binding?.inputEditTextEmail?.text.toString()
                )
            )
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
    }

}