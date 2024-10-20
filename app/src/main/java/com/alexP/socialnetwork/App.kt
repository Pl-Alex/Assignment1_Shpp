package com.alexP.socialnetwork

import android.app.Application
import com.alexp.contactsprovider.ContactsProvider

class App : Application() {

    val contactService = ContactsProvider()
}