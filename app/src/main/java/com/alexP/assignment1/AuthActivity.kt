package com.alexP.assignment1

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.alexP.assignment1.databinding.AuthActivityBinding
import com.alexP.assignment1.validator.EmailValidator
import com.alexP.assignment1.validator.EmptyValidator
import com.alexP.assignment1.validator.PasswordValidator
import com.alexP.assignment1.validator.base.BaseValidator
import java.util.Locale

const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_EMAIL_VALUE = "PREF_EMAIL_VALUE"
const val PREF_PASSWORD_VALUE = "PREF_PASSWORD_VALUE"

class AuthActivity : BaseActivity<AuthActivityBinding>() {

    private lateinit var preferences: SharedPreferences
    override fun inflate(inflater: LayoutInflater): AuthActivityBinding {
        return AuthActivityBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        checkSharedPreferences()
        setListeners()
    }

    private fun checkSharedPreferences() {
        //TODO Змінити логіку, щоб данні з SharedPreferences зчитувались лише, коли останній раз була встановлена галочка у полі "remember me"
        binding.inputEditTextEmail.setText(preferences.getString(PREF_EMAIL_VALUE, ""))
        binding.inputEditTextPassword.setText(preferences.getString(PREF_PASSWORD_VALUE, ""))
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {
            onRegisterButtonPressed()
        }
    }

    private fun onRegisterButtonPressed() {
// TODO винести у viewmodel шматки коду, які не потрібні для актівіті і не потребують контексту
        val email = binding.inputEditTextEmail.text.toString()
        val password = binding.inputEditTextPassword.text.toString()

        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        binding.inputLayoutEmail.error =
            if (emailValidations.isSuccess.not()) getString(emailValidations.message) else null

        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        binding.inputLayoutPassword.error =
            if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        if (emailValidations.isSuccess && passwordValidations.isSuccess) {
            preferences.edit()
                .putString(PREF_EMAIL_VALUE, email)
                .putString(PREF_PASSWORD_VALUE, password)
                .apply()

            val intent = Intent(this, MyProfileActivity::class.java)
            intent.putExtra("email", parseEmail(email))
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
    }

    private fun parseEmail(email: String): String {
        val namePart = email.substringBefore('@')

        return namePart.split('.')
            .joinToString(" ") { it ->
                it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
    }
}