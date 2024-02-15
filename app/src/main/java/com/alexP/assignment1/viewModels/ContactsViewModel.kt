package com.alexP.assignment1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.model.ContactsListener
import com.alexP.assignment1.model.ContactsService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val contactsServices: ContactsService,

) : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    private val deletedContacts = mutableListOf<Contact>()

    private var cleanDeletedContactsJob: Job? = null

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

    private fun loadContacts() {
        contactsServices.addListener(listener)
    }

    fun deleteContact(contact: Contact) {
        deletedContacts.add(contact)
        contactsServices.deleteContact(contact)

        cleanDeletedContactsJob?.cancel()
        cleanDeletedContactsJob = viewModelScope.launch {
            delay(5000)
            deletedContacts.remove(contact)
        }
    }

    fun recoverContacts() {
        deletedContacts.forEach {
            contactsServices.addContact(it)
        }
        deletedContacts.clear()
    }

    fun addContact(contact: Contact) {
        contactsServices.addContact(contact.copy(id = contactsServices.getNewId()))

    }
}