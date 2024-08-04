package com.alexP.assignment1.adapters

import com.alexp.contactsprovider.data.Contact

interface IContactActionListener {
    fun onContactDelete(contact: Contact)
}