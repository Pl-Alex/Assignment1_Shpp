package com.alexP.socialnetwork.ui.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alexP.socialnetwork.utils.applyWindowInsets


abstract class BaseFragment<out VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding
        get() = _binding!!

    abstract fun inflate(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().enableEdgeToEdge()
        _binding = inflate(layoutInflater, container)
        binding.root.applyWindowInsets()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}