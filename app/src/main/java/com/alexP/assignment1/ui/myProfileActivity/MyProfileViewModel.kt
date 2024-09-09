package com.alexP.assignment1.ui.myProfileActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val dataStore: DataStoreProvider,
) : ViewModel() {

    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.readUsername().collect { username ->
                _myProfileState.update {
                    MyProfileState(username = username)
                }
            }
        }
    }

    fun cleanStorage() {
        viewModelScope.launch {
            dataStore.cleanStorage()
        }
    }

    companion object {
        fun createFactory(dataStore: DataStoreProvider): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    MyProfileViewModel(dataStore)
                }
            }
    }

}