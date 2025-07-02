package com.example.contact.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.entities.Contact
import com.example.contact.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactViewModel(
    val repository: ContactRepository
) : ViewModel(){
    private  val _state = MutableStateFlow<AppState>(AppState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch{
            repository.getContacts().collectLatest { value ->
            _state.value = AppState.Data(value)
          }
        }
    }

    fun upsertContact(contact: Contact) = viewModelScope.launch{
        repository.upsertContact(contact)
    }
    fun deleteContact(contact: Contact) = viewModelScope.launch{
        repository.deleteContact(contact)
    }
}

sealed class  AppState{
    object  Loading : AppState()

    class Data(val data : List<Contact>) : AppState()
}