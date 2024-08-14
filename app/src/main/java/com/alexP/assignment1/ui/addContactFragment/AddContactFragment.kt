package com.alexP.assignment1.ui.addContactFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.FragmentDialogAddContactBinding
import com.alexP.assignment1.utils.applyWindowInsets
import com.alexP.assignment1.utils.loadCircularImage
import com.alexp.contactsprovider.data.Contact
import kotlinx.coroutines.launch


class AddContactFragment(
    private val onSaveAction: (Contact) -> Unit,
) : DialogFragment() {

    private lateinit var binding: FragmentDialogAddContactBinding
    private val vm: AddContactViewModel by viewModels()

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@registerForActivityResult
            try {
                vm.setGalleryUri(uri)
                binding.imageViewProfileImage.loadCircularImage(uri.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().enableEdgeToEdge()
        binding = FragmentDialogAddContactBinding.inflate(inflater, container, false)
        binding.root.applyWindowInsets()

        observeValues()
        binding.topBar.setNavigationOnClickListener {
            dismiss()
        }
        setListeners()

        return binding.root
    }

    private fun observeValues() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.galleryUri.collect { uri ->
                uri?.let {
                    binding.imageViewProfileImage.loadCircularImage(it.toString())
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            buttonSave.setOnClickListener { onButtonSavePressed() }
            imageViewAddProfileImage.setOnClickListener { galleryLauncher.launch("image/*") }

            inputEditTextUsername.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateUsername() }
            inputEditTextCareer.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateCareer() }
            inputEditTextEmail.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateEmail() }
            inputEditTextPhone.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validatePhone() }
            inputEditTextAddress.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateAddress() }
            inputEditTextDateOfBirth.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) validateDateOfBirth() }
        }
    }

    private fun validateUsername(): Boolean {
        val validationResult = vm.validateUsername(binding.inputEditTextUsername.text.toString())
        binding.inputLayoutUsername.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun validateCareer(): Boolean {
        val validationResult = vm.validateCareer(binding.inputEditTextCareer.text.toString())
        binding.inputLayoutCareer.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun validateEmail(): Boolean {
        val validationResult = vm.validateEmail(binding.inputEditTextEmail.text.toString())
        binding.inputLayoutEmail.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun validatePhone(): Boolean {
        val validationResult = vm.validatePhone(binding.inputEditTextPhone.text.toString())
        binding.inputLayoutPhone.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun validateAddress(): Boolean {
        val validationResult = vm.validateAddress(binding.inputEditTextAddress.text.toString())
        binding.inputLayoutAddress.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun validateDateOfBirth(): Boolean {
        val validationResult =
            vm.validateDateOfBirth(binding.inputEditTextDateOfBirth.text.toString())
        binding.inputLayoutDateOfBirth.error = validationResult?.let { getString(it) } ?: ""
        return validationResult == null
    }

    private fun onButtonSavePressed() {
        if (isAnyEnteredDataInvalid()) return

        val contact = Contact(
            id = -1,
            photo = vm.galleryUri.toString(),
            fullName = binding.inputEditTextUsername.text.toString(),
            career = binding.inputEditTextCareer.text.toString(),
            email = binding.inputEditTextEmail.text.toString(),
            phone = binding.inputEditTextPhone.text.toString(),
            address = binding.inputEditTextAddress.text.toString(),
            dateOfBirth = binding.inputEditTextDateOfBirth.text.toString()
        )
        onSaveAction(contact)
        dismiss()
    }

    private fun isAnyEnteredDataInvalid(): Boolean {
        val isUsernameValid = validateUsername()
        val isCareerValid = validateCareer()
        val isEmailValid = validateEmail()
        val isPhoneValid = validatePhone()
        val isAddressValid = validateAddress()
        val isDateOfBirthValid = validateDateOfBirth()

        return !(isUsernameValid && isCareerValid && isEmailValid && isPhoneValid && isAddressValid && isDateOfBirthValid)
    }
}

class MyFragmentFactory(private val onSaveAction: (Contact) -> Unit) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            AddContactFragment::class.java -> AddContactFragment(onSaveAction)
            else -> super.instantiate(classLoader, className)
        }
}