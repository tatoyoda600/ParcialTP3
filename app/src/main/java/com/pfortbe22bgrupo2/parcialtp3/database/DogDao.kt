package com.pfortbe22bgrupo2.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity

@Dao
interface DogDao {
    @Query("SELECT * FROM adoption_list")
    fun getAdoptionList(): List<DogEntity>

    @Query("SELECT * FROM adoption_list WHERE id = :id")
    fun getAdoptionById(id: Int): DogEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAdoption(dog: DogEntity): Long

    @Delete
    fun deleteAdoption(dog: DogEntity)
}