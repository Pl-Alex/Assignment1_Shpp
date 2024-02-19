package com.alexP.assignment1.dataProviders

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.viewModels.ContactsViewModel

class ContactsLoader(private val context: Context, private val viewModel: ContactsViewModel) {

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    fun loadContacts() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as AppCompatActivity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            fetchContacts()
        }
    }

    private fun fetchContacts() {
        val resolver: ContentResolver = context.contentResolver
        val cursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            val nameColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
            while (cursor.moveToNext()) {

                val fullname = cursor.getString(nameColumnIndex) ?: ""
                val career = ""
                val email = ""
                val phone = cursor.getString(phoneNumberColumnIndex) ?: ""
                val photo = cursor.getString(photoColumnIndex) ?: ""
                val address = ""
                val dateOfBirth = ""

                val contact = Contact(
                    id = -1,
                    photo = photo,
                    fullName = fullname,
                    career = career,
                    email = email,
                    phone = phone,
                    address = address,
                    dateOfBirth = dateOfBirth
                )
                viewModel.addContact(contact)
            }
        }
    }
}