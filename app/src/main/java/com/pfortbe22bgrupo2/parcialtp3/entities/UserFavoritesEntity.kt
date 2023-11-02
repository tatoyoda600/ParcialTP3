package com.pfortbe22bgrupo2.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user_favorites", primaryKeys = ["username", "dog_id"])
class UserFavoritesEntity(
    username: String,
    dog_id: Int
) {
    @ColumnInfo(name = "username")
    val username: String

    @ColumnInfo(name = "dog_id")
    val dog_id: Int

    init {
        this.username = username
        this.dog_id = dog_id
    }
}