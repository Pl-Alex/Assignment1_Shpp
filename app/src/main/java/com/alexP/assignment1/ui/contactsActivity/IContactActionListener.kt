package com.alexP.assignment1.ui.contactsActivity

import com.alexp.contactsprovider.data.Contact

interface IContactActionListener {
    fun onContactDelete(contact: Contact)
}