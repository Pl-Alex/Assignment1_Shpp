package com.alexP.assignment1.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.FragmentDialogAddContactBinding
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.utils.validator.EmailValidator
import com.alexP.assignment1.utils.validator.EmptyValidator
import com.alexP.assignment1.utils.validator.base.BaseValidator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AddContactFragment : DialogFragment() {

    interface OnContactSavedListener {
        fun onContactSaved(contact: Contact)
    }

    private lateinit var binding: FragmentDialogAddContactBinding
    private var listener: OnContactSavedListener? = null

    private var galleryUri: Uri? = null

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        galleryUri = it
        if(galleryUri == null) return@registerForActivityResult
        try {
            Glide.with(this).load(galleryUri).apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_contact_image)
                .error(R.drawable.default_contact_image)
                .into(binding.imageViewProfileImage)
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

        val toolbar = binding.topBar
        toolbar.setNavigationOnClickListener {
            dismiss()
        }

        setListeners()

        return binding.root
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
            photo = galleryUri.toString(),
            fullName = binding.inputEditTextUsername.text.toString(),
            career = binding.inputEditTextCareer.text.toString(),
            email = binding.inputEditTextEmail.text.toString(),
            phone = binding.inputEditTextPhone.text.toString(),
            address = binding.inputEditTextAddress.text.toString(),
            dateOfBirth = binding.inputEditTextDateOfBirth.text.toString()
        )

        listener?.onContactSaved(contact)
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

        var hasError = false

        editTexts.forEach { (editText, inputLayout) ->
            val text = editText.text.toString()
            val validations = when (editText) {
                binding.inputEditTextEmail -> {
                    BaseValidator.validate(EmptyValidator(text), EmailValidator(text))
                }
                else -> {
                    BaseValidator.validate(EmptyValidator(text))
                }
            }

            inputLayout.error = if (!validations.isSuccess) {
                getString(validations.message)
            } else {
                null
            }

            if (!validations.isSuccess) {
                hasError = true
            }
        }

        return hasError
    }

    fun setOnContactSavedListener(listener: OnContactSavedListener) {
        this.listener = listener
    }
}