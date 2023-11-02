package com.pfortbe22bgrupo2.parcialtp3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.DogImageEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity

@Database(entities = [DogEntity::class, DogImageEntity::class, UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao
    abstract fun dogImagesDao(): DogImagesDao
    abstract fun userDao(): UserDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "parcialtp3DB"
                    ).build()
                    //).addMigrations().allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}