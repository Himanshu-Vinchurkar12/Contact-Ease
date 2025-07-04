package com.example.contact.data.DataBase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DataBaseInit {
    var db : ContactDataBase? = null
    fun  getDatabase(context : Context) : ContactDataBase{
        if (db == null){
            val MIGRATION_2_3 = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE contact_table ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
                }
            }
            db = Room.databaseBuilder(
                context,
                ContactDataBase::class.java,
                "contact_database.sql"
            ).addMigrations(MIGRATION_2_3)
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build()
        }
        return  db!!
    }



}