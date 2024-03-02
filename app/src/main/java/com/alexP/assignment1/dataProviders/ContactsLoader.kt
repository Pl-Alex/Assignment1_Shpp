package com.alexP.assignment1.dataProviders

import android.content.ContentResolver
import android.provider.ContactsContract
import com.alexP.assignment1.model.Contact

class ContactsLoader {

    fun fetchContacts(resolver: ContentResolver): MutableList<Contact> {
        val contacts = mutableListOf<Contact>()
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
                contacts.add(contact)
            }
        }
        return contacts
    }
}