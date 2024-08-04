package com.alexP.assignment1

import android.app.Application
import com.alexp.contactsprovider.data.ContactsProvider

class App : Application() {

    val contactService = ContactsProvider()
}