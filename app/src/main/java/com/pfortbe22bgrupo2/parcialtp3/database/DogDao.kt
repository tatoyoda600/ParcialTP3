package com.pfortbe22bgrupo2.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import javax.inject.Inject

@Dao
interface DogDao  {
    @Query("SELECT * FROM adoption_list")
    fun getAdoptionList(): List<DogEntity>

    @Query("SELECT * FROM adoption_list WHERE id = :id")
    fun getAdoptionById(id: Int): DogEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAdoption(dog: DogEntity): Long

    @Query("SELECT * FROM adoption_list WHERE name = :name AND owner_username = :owner_username")
    fun getAdoptionByName(name: String, owner_username: String): DogEntity?

    @Delete
    fun deleteAdoption(dog: DogEntity)
}