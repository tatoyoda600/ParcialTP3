package com.pfortbe22bgrupo2.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.DogImageEntity

@Dao
interface DogImagesDao {
    @Query("SELECT * FROM dog_images WHERE dog_id = :id")
    fun getDogImagesById(id: Int): List<DogImageEntity>

    @Query("SELECT MAX(count) FROM dog_images WHERE dog_id = :id")
    fun getDogImageCountById(id: Int): Int?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDogImage(dogImage: DogImageEntity)

    @Query("DELETE FROM dog_images WHERE dog_id = :id")
    fun deleteDogImagesById(id: Int)
}