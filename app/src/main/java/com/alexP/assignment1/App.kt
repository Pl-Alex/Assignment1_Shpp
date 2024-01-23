package com.alexP.assignment1

import android.app.Application
import com.alexP.assignment1.model.ContactsService

class App : Application() {

    val contactService = ContactsService()
}