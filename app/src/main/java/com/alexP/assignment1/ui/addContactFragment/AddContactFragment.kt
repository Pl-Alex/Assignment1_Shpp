package com.alexP.assignment1.ui.addContactFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexP.assignment1.databinding.FragmentDialogAddContactBinding
import com.alexP.assignment1.utils.applyWindowInsets
import com.alexP.assignment1.utils.getValidationResultMessage
import com.alexP.assignment1.utils.loadCircularImage
import com.alexp.contactsprovider.data.Contact
import com.alexp.textvalidation.data.validator.EmailValidator
import com.alexp.textvalidation.data.validator.EmptyValidator
import com.alexp.textvalidation.data.validator.base.BaseValidator
import com.alexp.textvalidation.data.validator.base.ValidationResult
import kotlinx.coroutines.launch


class AddContactFragment(
    private val onSaveAction: (Contact) -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentDialogAddContactBinding
    private lateinit var viewModel: AddContactViewModel

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@registerForActivityResult
            try {
                viewModel.setGalleryUri(uri)
                binding.imageViewProfileImage.loadCircularImage(uri.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddContactBinding.inflate(inflater, container, false)
        binding.root.applyWindowInsets()

        viewModel = ViewModelProvider(this)[AddContactViewModel::class.java]

        observeValues()
        val toolbar = binding.topBar
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
        setListeners()

        return binding.root
    }

    private fun observeValues() {
        lifecycleScope.launch {
            viewModel.galleryUri.collect { uri ->
                uri?.let {
                    binding.imageViewProfileImage.loadCircularImage(it.toString())
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonSave.setOnClickListener {
            onButtonSavePressed()
        }
        binding.imageViewAddProfileImage.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

    private fun onButtonSavePressed() {
        if (enteredDataIsInvalid()) return

        val contact = Contact(
            id = -1,
            photo = viewModel.galleryUri.toString(),
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

    private fun enteredDataIsInvalid(): Boolean {
        val editTexts = listOf(
            Pair(binding.inputEditTextEmail, binding.inputLayoutEmail),
            Pair(binding.inputEditTextCareer, binding.inputLayoutCareer),
            Pair(binding.inputEditTextUsername, binding.inputLayoutUsername),
            Pair(binding.inputEditTextPhone, binding.inputLayoutPhone),
            Pair(binding.inputEditTextAddress, binding.inputLayoutAddress),
            Pair(binding.inputEditTextDateOfBirth, binding.inputLayoutDateOfBirth)
        )

        var noErrors = true

        editTexts.forEach { (editText, inputLayout) ->
            val text = editText.text.toString()
            val validations = when (editText) {
                binding.inputEditTextEmail -> {
                    BaseValidator.validate(
                        EmptyValidator(
                            text
                        ), EmailValidator(text)
                    )
                }

                else -> {
                    BaseValidator.validate(
                        EmptyValidator(
                            text
                        )
                    )
                }
            }

            if (validations == ValidationResult.SUCCESS) {
                inputLayout.error = null
            } else {
                inputLayout.error = getString(getValidationResultMessage(validations))
                noErrors = false
            }
        }

        return !noErrors
    }
}

class MyFragmentFactory(private val onSaveAction: (Contact) -> Unit) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            AddContactFragment::class.java -> AddContactFragment(onSaveAction)
            else -> super.instantiate(classLoader, className)
        }
}