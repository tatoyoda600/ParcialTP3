package com.pfortbe22bgrupo2.parcialtp3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adoption_list")
class DogEntity(
    id: Int,
    name: String,
    age: Int,
    location: String,
    sex: String,
    weight: Float,
    owner_username: String,
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

    @ColumnInfo(name = "owner_username")
    val owner_username: String

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
        this.owner_username = owner_username
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
        owner_username: String,
        breed: String,
        subbreed: String?,
        text: String
    ): this(0, name, age, location, sex, weight, owner_username, breed, subbreed, text)
}