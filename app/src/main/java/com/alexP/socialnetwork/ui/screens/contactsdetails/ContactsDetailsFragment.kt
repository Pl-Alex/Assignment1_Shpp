package com.alexP.socialnetwork.ui.screens.contactsdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexP.socialnetwork.R
import com.alexP.socialnetwork.databinding.FragmentContactDetailsBinding

class ContactsDetailsFragment : Fragment(R.layout.fragment_contact_details) {

    private lateinit var binding: FragmentContactDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactDetailsBinding.bind(view)

        binding.topBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }
}