package com.pfortbe22bgrupo2.parcialtp3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.UserFavoritesEntity

@Dao
interface UserFavoritesDao {
    @Query("SELECT dog_id FROM user_favorites WHERE username = :username")
    fun getFavoriteIDsByUsername(username: String): List<Int>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFavorite(favorite: UserFavoritesEntity)

    @Query("SELECT * FROM user_favorites WHERE username = :username AND dog_id = :id")
    fun getUserFavoriteById(username: String, id: Int): UserFavoritesEntity?

    @Delete
    fun deleteFavorite(favorite: UserFavoritesEntity)
}