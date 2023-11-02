package com.pfortbe22bgrupo2.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_images", primaryKeys = ["dog_id", "count"])
class DogImageEntity(
    dog_id: Int,
    count: Int,
    image_url: String
) {
    @ColumnInfo(name = "dog_id")
    val dog_id: Int

    @ColumnInfo(name = "count")
    val count: Int

    @ColumnInfo(name = "image_url")
    val image_url: String

    init {
        this.dog_id = dog_id
        this.count = count
        this.image_url = image_url
    }
}