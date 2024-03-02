package com.alexP.assignment1.adapters

import com.alexP.assignment1.model.Contact

interface IContactActionListener {
    fun onContactDelete(contact: Contact)
}