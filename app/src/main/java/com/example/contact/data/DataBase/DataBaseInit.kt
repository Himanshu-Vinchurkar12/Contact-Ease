package com.example.contact.data.DataBase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DataBaseInit {
    var db : ContactDataBase? = null
    fun  getDatabase(context : Context) : ContactDataBase{
        if (db == null){
            db =  Room.databaseBuilder(
                context,
                ContactDataBase::class.java,
                "contact_database.sql"
            ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build()
        }
        return  db!!
    }

//    fun  getDatabase(context : Context) =
//          Room.databaseBuilder(
//          context,
//          ContactDataBase::class.java,
//           "contact_database"
//           )
//              .build()

}