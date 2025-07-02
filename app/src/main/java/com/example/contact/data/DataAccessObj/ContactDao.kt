package com.example.contact.data.DataAccessObj

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.contact.data.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao{

    @Upsert
    suspend fun upsertContact(contact: Contact)

     @Delete
     suspend fun deleteContact(contact: Contact)

     @Query("SELECT * FROM contact_table")
     fun getContacts(): Flow<List<Contact>>


}