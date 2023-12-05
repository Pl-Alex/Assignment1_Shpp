package com.alexP.assignment1

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexP.assignment1.databinding.AuthActivityBinding
import com.alexP.assignment1.validator.EmailValidator
import com.alexP.assignment1.validator.EmptyValidator
import com.alexP.assignment1.validator.PasswordValidator
import com.alexP.assignment1.validator.base.BaseValidator
import java.util.Locale

class AuthActivity : AppCompatActivity() {

    lateinit var binding: AuthActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonRegister.setOnClickListener {
            onRegisterButtonPressed()
        }

    }

    private fun onRegisterButtonPressed() {

        val email = binding.inputEditTextEmail.text.toString()
        val password = binding.inputEditTextPassword.text.toString()

        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        binding.inputLayoutEmail.error =
            if (!emailValidations.isSuccess) getString(emailValidations.message) else null

        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        binding.inputLayoutPassword.error =
            if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        if (emailValidations.isSuccess && passwordValidations.isSuccess) {
            val intent = Intent(this, MyProfileActivity::class.java)
            intent.putExtra("email", parseEmail(email))
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    fun parseEmail(email: String): String {
        val namePart = email.substringBefore('@')

        return namePart.split('.')
            .joinToString(" ") { it ->
                it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
    }
}