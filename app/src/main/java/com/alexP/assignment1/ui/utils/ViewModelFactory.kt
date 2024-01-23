package com.alexP.assignment1.ui.utils

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexP.assignment1.App
import com.alexP.assignment1.viewModels.ContactsViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            ContactsViewModel::class.java -> {
                ContactsViewModel(app.contactService)
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Activity.factory() = ViewModelFactory(applicationContext as App)