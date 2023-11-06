package com.pfortbe22bgrupo2.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pfortbe22bgrupo2.parcialtp3.entities.AdoptedDogEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import javax.inject.Inject

@Dao
interface AdoptedDogDao  {
    @Query("SELECT * FROM adopted_list WHERE new_owner_username = :new_owner_username")
    fun getAdoptedDogListByUser(new_owner_username: String): List<AdoptedDogEntity>

    @Query("SELECT * FROM adopted_list WHERE new_owner_username = :new_owner_username AND id = :id")
    fun getUserAdoptedDogById(new_owner_username: String, id: Int): AdoptedDogEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAdoptedDog(dog: AdoptedDogEntity): Long

    @Delete
    fun deleteAdoptedDog(dog: AdoptedDogEntity)
}