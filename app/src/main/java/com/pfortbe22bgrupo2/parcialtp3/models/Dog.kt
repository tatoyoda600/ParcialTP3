package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.pfortbe22bgrupo2.parcialtp3.entities.AdoptedDogEntity

import androidx.annotation.RequiresApi

import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Dog(
    val id: Int,
    val name: String,
    val age: Int,
    val location: String,
    val sex: String,
    val weight: Float,
    val owner_username: String,
    val breed: String,
    val subbreed: String?,
    val text: String,
    val image_urls: Array<String>?,
    var isFavorite: Boolean
): Parcelable {
    constructor(
        name: String,
        age: Int,
        location: String,
        sex: String,
        weight: Float,
        owner_username: String,
        breed: String,
        subbreed: String? = null,
        text: String,
        image_urls: Array<String>?,
        isFavorite: Boolean
    ): this(0, name, age, location, sex, weight, owner_username, breed, subbreed, text, image_urls, isFavorite)


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.createStringArray(),
        parcel.toString().toBoolean()

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(location)
        parcel.writeString(sex)
        parcel.writeFloat(weight)
        parcel.writeString(owner_username)
        parcel.writeString(breed)
        parcel.writeString(subbreed)
        parcel.writeString(text)
        parcel.writeStringArray(image_urls)
        parcel.toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toEntity(): DogEntity {
        var output: DogEntity? = null

        if (id != 0) {
            output = DogEntity(id, name, age, location, sex, weight, owner_username, breed, subbreed, text, isFavorite)
        }
        else {
            output = DogEntity(name, age, location, sex, weight, owner_username, breed, subbreed, text, isFavorite)
        }

        return output
    }

    fun toAdoptedEntity(newOwnerUsername: String): AdoptedDogEntity {
        var output: AdoptedDogEntity? = null

        if (id != 0) {
            output = AdoptedDogEntity(id, name, age, location, sex, weight, owner_username, newOwnerUsername, breed, subbreed, text, isFavorite)
        }
        else {
            output = AdoptedDogEntity(name, age, location, sex, weight, owner_username, newOwnerUsername, breed, subbreed, text, isFavorite)
        }

        return output
    }

    companion object {
        const val MALE = "Macho"
        const val FEMALE = "Hembra"

        @JvmField
        val CREATOR: Parcelable.Creator<Dog> = object : Parcelable.Creator<Dog> {
           // @RequiresApi(Build.VERSION_CODES.Q)
            override fun createFromParcel(parcel: Parcel): Dog {
                return Dog(parcel)
            }

            override fun newArray(size: Int): Array<Dog?> {
                return arrayOfNulls(size)
            }
        }

        fun createFromEntity(dogEntity: DogEntity, databaseHandler: DatabaseHandler): Dog {
            val imageUrls = databaseHandler.getDogImagesById(dogEntity.id)

            return Dog(
                dogEntity.id,
                dogEntity.name,
                dogEntity.age,
                dogEntity.location,
                dogEntity.sex,
                dogEntity.weight,
                dogEntity.owner_username,
                dogEntity.breed,
                dogEntity.subbreed,
                dogEntity.text,
                imageUrls.toTypedArray(),
                dogEntity.isFavorite
            )
        }

        fun createFromEntity(adoptedDogEntity: AdoptedDogEntity, databaseHandler: DatabaseHandler): Dog {
            val imageUrls = databaseHandler.getDogImagesById(adoptedDogEntity.id)

            return Dog(
                adoptedDogEntity.id,
                adoptedDogEntity.name,
                adoptedDogEntity.age,
                adoptedDogEntity.location,
                adoptedDogEntity.sex,
                adoptedDogEntity.weight,
                adoptedDogEntity.original_owner_username,
                adoptedDogEntity.breed,
                adoptedDogEntity.subbreed,
                adoptedDogEntity.text,
                imageUrls.toTypedArray(),
                adoptedDogEntity.isFavorite
            )
        }
    }

    enum class Location(val displayName : String) {
        BUENOS_AIRES("Buenos Aires"),
        CABA("CABA"),
        CATAMARCA("Catamarca"),
        CHACO("Chaco"),
        CHUBUT("Chubut"),
        CORDOBA("Cordoba"),
        CORRIENTES("Corrientes"),
        ENTRE_RIOS("Entre Rios"),
        FORMOSA("Formosa"),
        JUJUY("Jujuy"),
        LA_PAMPA("La Pampa"),
        LA_RIOJA("La Rioja"),
        MENDOZA("Mendoza"),
        MISIONES("Misiones"),
        NEUQUEN("Neuquen"),
        RIO_NEGRO("Rio Negro"),
        SALTA("Salta"),
        SAN_JUAN("San Juan"),
        SAN_LUIS("San Luis"),
        SANTA_CRUZ("Santa Cruz"),
        SANTA_FE("Santa Fe"),
        SANTIAGO_DEL_ESTERO("Santiago del Estero"),
        TIERRA_DEL_FUEGO("Tierra del Fuego"),
        TUCUMAN("Tucuman")
    }
}
