package com.alexP.assignment1.ui.authActivity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexP.assignment1.databinding.ActivityAuthBinding
import com.alexP.assignment1.ui.BaseActivity
import com.alexP.assignment1.ui.myProfileActivity.MyProfileActivity
import kotlinx.coroutines.launch

const val EMAIL_NAV_KEY = "email"

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private lateinit var viewModel: AuthViewModel

    override fun inflate(inflater: LayoutInflater): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, AuthViewModelFactory(this))[AuthViewModel::class.java]

        setListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.authState.collect { authState ->
                    if (authState.isAutologin) {
                        navToNextScreen(authState.navEmail)
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
        }
    }

    private fun onRegisterButtonPressed() {
        val emailText = binding.inputEditTextEmail?.text.toString()
        val passwordText = binding.inputEditTextPassword?.text.toString()
        val isRememberMeChecked = binding.checkBoxRemember?.isChecked
        if (viewModel.validateEmail(emailText).isSuccess
            && viewModel.validatePassword(passwordText).isSuccess
        ) {
            onNavigate(emailText, passwordText, isRememberMeChecked)
        }
    }

    private fun onNavigate(email: String, password: String, isRememberMeChecked: Boolean?) {
        viewModel.saveAutologin(email, password, isRememberMeChecked)
        navToNextScreen(email)
    }

    private fun navToNextScreen(email: String) {
        val intent = Intent(this, MyProfileActivity::class.java)
        intent.putExtra(
            EMAIL_NAV_KEY, viewModel.parseEmail(email)
        )
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }
}