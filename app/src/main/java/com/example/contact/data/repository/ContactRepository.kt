package com.example.contact.data.repository

import com.example.contact.data.DataAccessObj.ContactDao
import com.example.contact.data.entities.Contact

class ContactRepository (
      val dao: ContactDao
)
{
    suspend fun upsertContact(contact: Contact) =
        dao.upsertContact(contact)

    suspend fun deleteContact(contact: Contact) =
        dao.deleteContact(contact)

    fun getContacts() = dao.getContacts()
}