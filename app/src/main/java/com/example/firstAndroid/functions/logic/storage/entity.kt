package com.example.firstAndroid.functions.logic.storage

import androidx.room.*
import androidx.room.ColumnInfo
import androidx.room.Embedded

/** @Entity(primaryKeys = {"firstName", "lastName"}) */
@Entity(tableName = "user", indices = [Index("name"), Index(value = ["last_name"])])
class EntityUser {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "age")
    @Ignore
    var age: Int? = null

    @Embedded(prefix = "FF_")
    var address: Address? = null
}

class Address {
    var street: String? = null
    var state: String? = null
    var city: String? = null

    @ColumnInfo(name = "post_code")
    var postCode = 0
}