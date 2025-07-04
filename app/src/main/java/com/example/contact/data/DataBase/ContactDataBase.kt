package com.example.contact.data.DataBase

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contact.data.DataAccessObj.ContactDao
import com.example.contact.data.entities.Contact

@Database(entities = [Contact ::class], exportSchema = true, version = 3,  )
abstract class ContactDataBase : RoomDatabase() {

    abstract val dao : ContactDao

}