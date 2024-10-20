package com.alexp.contactsprovider

import android.content.ContentResolver
import android.provider.ContactsContract
import com.github.javafaker.Faker

typealias ContactsListener = (contacts: List<Contact>) -> Unit

class ContactsProvider {

    private var contacts = mutableListOf<Contact>()
        set(newValue) {
            field = newValue.sortedWith(compareByDescending { it.id }).toMutableList()
        }

    private val listeners = mutableListOf<ContactsListener>()

    init {
        val faker = Faker.instance()
        contacts = (0..40).map {
            Contact(
                id = it.toLong(),
                phone = faker.phoneNumber().phoneNumber(),
                photo = IMAGE_LINK,
                fullName = faker.name().name(),
                career = faker.job().title(),
                email = faker.internet().emailAddress(),
                address = faker.address().streetAddress(),
                dateOfBirth = faker.date().birthday().toString()
            )
        }.toMutableList()
    }

    fun addListener(listener: ContactsListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }

    fun removeListener(listener: ContactsListener) {
        listeners.remove(listener)
    }

    fun getNewId(): Long {
        return contacts.first().id + 1
    }

    fun deleteContact(contact: Contact) {
        val indexToDelete = contacts.indexOfFirst { it.id == contact.id }
        if (indexToDelete != -1) {
            contacts = ArrayList(contacts)
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addContact(contact: Contact) {
        val newContacts = ArrayList(contacts)
        newContacts.add(0, contact)
        contacts = ArrayList(newContacts)
        notifyChanges()
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }

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

    companion object {
        const val IMAGE_LINK =
            "https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640"
    }

}