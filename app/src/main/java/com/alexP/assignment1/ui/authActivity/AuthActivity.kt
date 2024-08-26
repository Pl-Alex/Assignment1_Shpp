package com.alexP.assignment1.ui.authActivity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexP.assignment1.databinding.ActivityAuthBinding
import com.alexP.assignment1.ui.BaseActivity
import com.alexP.assignment1.ui.myProfileActivity.MyProfileActivity
import com.alexP.assignment1.utils.getValidationResultMessage
import com.alexp.datastore.data.DataStoreProvider
import com.alexp.textvalidation.data.validateEmail
import com.alexp.textvalidation.data.validatePassword
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.launch

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private val vm: AuthViewModel by viewModels {
        AuthViewModel.createFactory(DataStoreProvider(this))
    }

    override fun inflate(inflater: LayoutInflater): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vm.authState.collect { authState ->
                    if (authState.isAutologin) {
                        navToNextScreen()
                    }
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            buttonRegister.setOnClickListener {
                onRegisterButtonPressed()
            }
            buttonSingInGoogle.setOnClickListener {
                Toast.makeText(
                    this@AuthActivity,
                    "Sing in Google button pressed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            inputEditTextEmail?.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateEmail() }
            inputEditTextPassword?.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validatePassword() }
        }
    }

    private fun onRegisterButtonPressed() {
        if (isAnyEnteredDataInvalid()) return
        val emailText = binding.inputEditTextEmail?.text.toString()
        val passwordText = binding.inputEditTextPassword?.text.toString()
        val isRememberMeChecked = binding.checkBoxRemember?.isChecked
        onNavigate(emailText, passwordText, isRememberMeChecked)

    }

    private fun isAnyEnteredDataInvalid(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return !(isEmailValid && isPasswordValid)
    }

    private fun onNavigate(email: String, password: String, isRememberMeChecked: Boolean?) {
        vm.saveAutologin(email, password, isRememberMeChecked)
        navToNextScreen()
    }

    private fun navToNextScreen() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private fun validateEmail(): Boolean {
        val validationResult = validateEmail(binding.inputEditTextEmail?.text.toString())
        binding.inputLayoutEmail.error =
            getValidationResultMessage(validationResult)?.let { getString(it) } ?: ""
        return validationResult == ValidationResult.SUCCESS
    }

    private fun validatePassword(): Boolean {
        val validationResult = validatePassword(binding.inputEditTextPassword?.text.toString())
        binding.inputLayoutPassword.error =
            getValidationResultMessage(validationResult)?.let { getString(it) } ?: ""
        return validationResult == ValidationResult.SUCCESS
    }
}