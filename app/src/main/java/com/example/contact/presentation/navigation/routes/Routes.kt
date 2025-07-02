package com.example.contact.presentation.navigation.routes


import kotlinx.serialization.Serializable

sealed class  Routes {
    @Serializable
    object  HomeScreen : Routes()

    @Serializable
    data class  AddEditScreen(val contactId : Int? ) : Routes()

    @Serializable
    data class DetailScreen(val contactId: Int?) : Routes()

}