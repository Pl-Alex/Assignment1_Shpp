package com.alexP.assignment1.ui.myProfileActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexp.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyProfileViewModel(
    val dataStore: DataStoreProvider
) : ViewModel() {

    private val _emailState = MutableStateFlow("")
    val emailState get() = _emailState.asStateFlow()

    fun cleanStorage(){
        viewModelScope.launch {
            dataStore.cleanStorage()
        }
    }

}

class MyProfileViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MyProfileViewModel::class.java -> {
                val dataStore = DataStoreProvider(context)
                MyProfileViewModel(dataStore)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}