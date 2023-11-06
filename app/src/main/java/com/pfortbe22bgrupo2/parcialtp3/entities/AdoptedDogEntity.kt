package com.pfortbe22bgrupo2.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adopted_list")
class AdoptedDogEntity(
    id: Int,
    name: String,
    age: Int,
    location: String,
    sex: String,
    weight: Float,
    original_owner_username: String,
    new_owner_username: String,
    breed: String,
    subbreed: String?,
    text: String
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int

    @ColumnInfo(name = "name")
    val name: String

    @ColumnInfo(name = "age")
    val age: Int

    @ColumnInfo(name = "location")
    val location: String

    @ColumnInfo(name = "sex")
    val sex: String

    @ColumnInfo(name = "weight")
    val weight: Float

    @ColumnInfo(name = "original_owner_username")
    val original_owner_username: String

    @ColumnInfo(name = "new_owner_username")
    val new_owner_username: String

    @ColumnInfo(name = "breed")
    val breed: String

    @ColumnInfo(name = "subbreed")
    val subbreed: String?

    @ColumnInfo(name = "text")
    val text: String

    init {
        this.id = id
        this.name = name
        this.age = age
        this.location = location
        this.sex = sex
        this.weight = weight
        this.original_owner_username = original_owner_username
        this.new_owner_username = new_owner_username
        this.breed = breed
        this.subbreed = subbreed
        this.text = text
    }

    constructor(
        name: String,
        age: Int,
        location: String,
        sex: String,
        weight: Float,
        original_owner_username: String,
        new_owner_username: String,
        breed: String,
        subbreed: String?,
        text: String
    ): this(0, name, age, location, sex, weight, original_owner_username, new_owner_username, breed, subbreed, text)
}