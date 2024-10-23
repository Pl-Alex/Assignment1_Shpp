package com.alexP.socialnetwork.ui.screens.contacts.adapter

import com.alexp.contactsprovider.Contact

interface IContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactDetails(contact: Contact)
}