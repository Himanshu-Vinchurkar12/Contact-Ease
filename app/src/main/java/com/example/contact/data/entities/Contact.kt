package com.example.contact.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,
    @ColumnInfo(name = "name")
    var name : String ,
    var phoneNo : String ,
    var email : String ,
    @ColumnInfo(name = "profile", defaultValue = "")
   var profile: ByteArray?= null,
    var dateOfEdit : Long,
)