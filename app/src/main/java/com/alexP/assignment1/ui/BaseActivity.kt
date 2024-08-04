package com.alexP.assignment1.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alexP.assignment1.utils.applyWindowInsets

abstract class BaseActivity<out VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding
        get() = _binding!!

    abstract fun inflate(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        _binding = inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.applyWindowInsets()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}