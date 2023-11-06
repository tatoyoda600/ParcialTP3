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
    text: String,
    isFavorite: Boolean
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

    @ColumnInfo(name = "text")
    val text: String

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean

    init {
        this.id = id
        this.name = name
        this.age = age
        this.location = location
        this.sex = sex
        this.weight = weight
        this.owner_username = owner_username
        this.text = text
        this.isFavorite = isFavorite
    }

    constructor(
        name: String,
        age: Int,
        location: String,
        sex: String,
        weight: Float,
        owner_username: String,
        text: String,
        isFavorite: Boolean
    ): this(0, name, age, location, sex, weight, owner_username, text, isFavorite)
}