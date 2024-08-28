package com.alexP.assignment1.ui.myProfileActivity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.ActivityMyProfileBinding
import com.alexP.assignment1.ui.BaseActivity
import com.alexP.assignment1.ui.authActivity.AuthActivity
import com.alexP.assignment1.utils.loadCircularImage
import com.alexP.assignment1.utils.parseEmail
import com.alexp.datastore.data.DataStoreProvider
import kotlinx.coroutines.launch

class MyProfileActivity : BaseActivity<ActivityMyProfileBinding>() {


    private val vm: MyProfileViewModel by viewModels{
        MyProfileViewModel.createFactory(DataStoreProvider(this))
    }

    override fun inflate(inflater: LayoutInflater): ActivityMyProfileBinding {
        return ActivityMyProfileBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imageViewProfileImage.loadCircularImage(IMAGE_LINK)

        setListeners()
        lifecycleScope.launch {
            vm.emailState.collect { email ->
                Log.i("333", "email in myProfile: $email")
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
        vm.cleanStorage()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    companion object {
        const val IMAGE_LINK =
            "https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640"
    }
}