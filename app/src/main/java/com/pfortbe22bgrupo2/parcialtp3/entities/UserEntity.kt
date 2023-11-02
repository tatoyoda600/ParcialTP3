package com.pfortbe22bgrupo2.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    username: String,
    name: String,
    phone: String,
    image_url: String
) {
    @PrimaryKey
    val username: String

    @ColumnInfo(name = "name")
    val name: String

    @ColumnInfo(name = "phone")
    val phone: String

    @ColumnInfo(name = "image_url")
    val image_url: String

    init {
        this.username = username
        this.name = name
        this.phone = phone
        this.image_url = image_url
    }
}