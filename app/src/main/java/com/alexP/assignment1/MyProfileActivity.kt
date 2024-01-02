package com.alexP.assignment1

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.ActivityMyProfileBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class MyProfileActivity : BaseActivity<ActivityMyProfileBinding>() {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    companion object {
        const val EMAIL = "email"
        const val IMAGE_LINK =
            "https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640"
        const val REMEMBER_STATE = "remember_state"
    }

    override fun inflate(inflater: LayoutInflater): ActivityMyProfileBinding {
        return ActivityMyProfileBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra(EMAIL)
        binding?.textViewNameSurname?.text = email

        binding?.let {
            Glide.with(this)
                .load(IMAGE_LINK)
                .into(it.imageViewProfileImage)
        }

        setListeners()
    }

    private fun setListeners() {
        binding?.buttonLogOut?.setOnClickListener {
            onLogOutButtonPressed()
        }
    }

    private fun onLogOutButtonPressed() {
        lifecycleScope.launch {
            cleanStorage()
        }
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private suspend fun cleanStorage() {
        dataStore.edit { pref ->
            pref[booleanPreferencesKey(REMEMBER_STATE)] = false
        }
    }
}