package com.alexP.socialnetwork.ui.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.contactsprovider.Contact
import com.alexp.contactsprovider.ContactsListener
import com.alexp.contactsprovider.ContactsProvider

class ContactsViewModel(
    private val contactsServices: ContactsProvider,
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

    private fun loadContacts() {
        contactsServices.addListener(listener)
    }

    companion object {
        fun createFactory(contactsServices: ContactsProvider): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ContactsViewModel(contactsServices)
                }
            }
    }
}