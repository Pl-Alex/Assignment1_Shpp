package com.alexP.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out VB : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: VB
    val binding
        get() = _binding

    abstract fun inflate(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding.root)
    }
}