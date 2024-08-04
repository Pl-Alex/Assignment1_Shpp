package com.alexP.assignment1.ui.myProfileActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val dataStore: DataStoreProvider,
) : ViewModel() {

    private val _emailState = MutableStateFlow("")
    val emailState get() = _emailState.asStateFlow()

    fun cleanStorage() {
        viewModelScope.launch {
            dataStore.cleanStorage()
        }
    }

    companion object {
        fun createFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyProfileViewModel(
                    dataStore = DataStoreProvider(context)
                )
            }
        }
    }

}