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
        val view = binding.root

        val toolbar = binding.topBar
        toolbar.setNavigationOnClickListener {
            dismiss()
        }

        setListeners()

        return view
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

        val fullname = binding.inputEditTextUsername.text.toString()
        val career = binding.inputEditTextCareer.text.toString()
        val email = binding.inputEditTextEmail.text.toString()
        val phone = binding.inputEditTextPhone.text.toString()
        val address = binding.inputEditTextAddress.text.toString()
        val dateOfBirth = binding.inputEditTextDateOfBirth.text.toString()

        if (enteredDataIsInvalid(
                fullname,
                career,
                email,
                phone,
                address,
                dateOfBirth
            )
        ) return

        val contact = Contact(
            id = -1,
            photo = galleryUri.toString(),
            fullName = fullname,
            career = career,
            email = email,
            phone = phone,
            address = address,
            dateOfBirth = dateOfBirth
        )

        listener?.onContactSaved(contact)
        dismiss()
    }

    private fun enteredDataIsInvalid(
        fullname: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        dateOfBirth: String
    ): Boolean {
        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        val careerValidations = BaseValidator.validate(
            EmptyValidator(career)
        )
        val fullnameValidations = BaseValidator.validate(
            EmptyValidator(fullname)
        )
        val phoneValidations = BaseValidator.validate(
            EmptyValidator(phone)
        )
        val addressValidations = BaseValidator.validate(
            EmptyValidator(address)
        )
        val dateOfBirthValidations = BaseValidator.validate(
            EmptyValidator(dateOfBirth)
        )

        binding.inputLayoutEmail.error = (if (!emailValidations.isSuccess) {
            getString(emailValidations.message)
        } else {
            ""
        }).toString()
        binding.inputLayoutCareer.error = (
                if (!careerValidations.isSuccess) {
                    getString(careerValidations.message)
                } else {
                    ""
                }
                ).toString()
        binding.inputLayoutUsername.error = (
                if (!fullnameValidations.isSuccess) {
                    getString(fullnameValidations.message)
                } else {
                    ""
                }
                ).toString()
        binding.inputLayoutPhone.error = (
                if (!phoneValidations.isSuccess) {
                    getString(phoneValidations.message)
                } else {
                    ""
                }
                ).toString()
        binding.inputLayoutAddress.error = (
                if (!addressValidations.isSuccess) {
                    getString(addressValidations.message)
                } else {
                    ""
                }
                ).toString()
        binding.inputLayoutDateOfBirth.error = (
                if (!dateOfBirthValidations.isSuccess) {
                    getString(dateOfBirthValidations.message)
                } else {
                    ""
                }
                ).toString()

        return !(emailValidations.isSuccess
                && careerValidations.isSuccess
                && fullnameValidations.isSuccess
                && phoneValidations.isSuccess
                && addressValidations.isSuccess
                && dateOfBirthValidations.isSuccess)

    }

    fun setOnContactSavedListener(listener: OnContactSavedListener) {
        this.listener = listener
    }
}