package com.alexP.assignment1.ui.addContactFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddContactViewModel : ViewModel() {
    private val _galleryUri = MutableStateFlow<Uri?>(null)
    val galleryUri get() = _galleryUri.asStateFlow()

    fun setGalleryUri(uri: Uri) {
        _galleryUri.value = uri
    }
}
