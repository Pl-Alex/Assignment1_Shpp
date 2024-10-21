package com.alexP.socialnetwork.ui.screens.main

import android.view.LayoutInflater
import com.alexP.socialnetwork.databinding.ActivityMainBinding
import com.alexP.socialnetwork.ui.screens.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflate(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

}