package com.alexp.contactsprovider

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

    companion object {
        const val IMAGE_LINK =
            "https://unsplash.com/photos/_vnKbf9K-Vo/download?ixid=M3wxMjA3fDB8MXxhbGx8MTE4fHx8fHx8Mnx8MTcwMTgxMDA2MHw&force=true&w=640"
    }

}