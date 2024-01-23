package com.alexP.assignment1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.model.ContactsListener
import com.alexP.assignment1.model.ContactsService

class ContactsViewModel(
    private val contactsServices: ContactsService
) : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    private val listener: ContactsListener = {
        _contacts.value = it
    }

    init {
        loadContacts()
    }

    override fun onCleared() {
        super.onCleared()
        contactsServices.removeListener(listener)
    }

    fun loadContacts() {
        contactsServices.addListener(listener)
    }

    fun deleteContact(contact: Contact){
        contactsServices.deleteContact(contact)
    }

}