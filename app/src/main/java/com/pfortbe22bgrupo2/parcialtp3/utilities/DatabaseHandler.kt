package com.pfortbe22bgrupo2.parcialtp3.utilities

import android.content.Context
import com.pfortbe22bgrupo2.parcialtp3.database.AppDatabase
import com.pfortbe22bgrupo2.parcialtp3.database.DogDao
import com.pfortbe22bgrupo2.parcialtp3.database.DogImagesDao
import com.pfortbe22bgrupo2.parcialtp3.database.UserDao
import com.pfortbe22bgrupo2.parcialtp3.entities.DogImageEntity
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity
import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class DatabaseHandler(context: Context) {
    val database: AppDatabase
    val dogDao: DogDao
    val dogImagesDao: DogImagesDao
    val userDao: UserDao

    companion object {

    }

    init {
        database = AppDatabase.getAppDatabase(context)!!
        dogDao = database.dogDao()
        dogImagesDao = database.dogImagesDao()
        userDao = database.userDao()
    }

    fun getAdoptionList(): List<Dog> {
        var output: MutableList<Dog> = mutableListOf()
        val entities = dogDao.getAdoptionList()

        for (dog in entities) {
            output.add(Dog.createFromEntity(dog, this))
        }

        return output
    }

    fun getAdoptionById(id: Int): Dog? {
        var output: Dog? = null
        val entity = dogDao.getAdoptionById(id)

        if (entity != null) {
            output = Dog.createFromEntity(entity, this)
        }

        return output
    }

    fun insertAdoption(dog: Dog): Boolean {
        val entity = dog.toEntity()
        if (entity != null) {
            try {
                val id = dogDao.insertAdoption(entity)
                insertDogImages(id.toInt(), dog.image_urls?: arrayOf())
                return true
            }
            catch (error: Exception) {
                return false
            }
        }
        return false
    }

    fun deleteAdoption(dog: Dog) {
        val entity = dog.toEntity()
        if (entity != null) {
            dogDao.deleteAdoption(entity)
        }
    }

    fun getDogImagesById(id: Int): List<String> {
        val output: MutableList<String> = mutableListOf()
        val entities = dogImagesDao.getDogImagesById(id)

        for (image in entities) {
            if (image.image_url != null) {
                output.add(image.image_url)
            }
        }

        return output.toList()
    }

    fun getDogImageCountById(id: Int): Int {
        return dogImagesDao.getDogImageCountById(id)?: 0
    }

    fun insertDogImages(dogId: Int, imageUrls: Array<String>) {
        var count = getDogImageCountById(dogId)
        for (image in imageUrls) {
            val entity = DogImageEntity(dogId, count + 1, image)
            dogImagesDao.insertDogImage(entity)
            count++
        }
    }

    fun getUserByUsername(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }

    fun insertUser(user: UserEntity): Boolean {
        try {
            userDao.insertUser(user)
            return true
        }
        catch (error: Exception) {
            return false
        }
    }

    fun deleteUser(user: UserEntity) {
        return userDao.deleteUser(user)
    }
}