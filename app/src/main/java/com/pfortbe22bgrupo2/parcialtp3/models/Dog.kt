package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
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
    val owner: String,
    val phone: String,
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
        owner: String,
        phone: String,
        text: String,
        image_urls: Array<String>?,
        isFavorite: Boolean
    ): this(0, name, age, location, sex, weight, owner_username, owner, phone, text, image_urls,isFavorite)


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
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
        parcel.writeString(owner)
        parcel.writeString(phone)
        parcel.writeString(text)
        parcel.writeStringArray(image_urls)
        parcel.toString()
        //parcel.writeBoolean(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toEntity(): DogEntity? {
        var output: DogEntity? = null

        if (id != 0) {
            output = DogEntity(id, name, age, location, sex, weight, owner_username, text, isFavorite)
        }
        else {
            output = DogEntity(name, age, location, sex, weight, owner_username, text, isFavorite)
        }

        return output
    }

    companion object {
        const val MALE = "Macho"
        const val FEMALE = "Hembra"

        @JvmField
        val CREATOR: Parcelable.Creator<Dog> = object : Parcelable.Creator<Dog> {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun createFromParcel(parcel: Parcel): Dog {
                return Dog(parcel)
            }

            override fun newArray(size: Int): Array<Dog?> {
                return arrayOfNulls(size)
            }
        }

        fun createFromEntity(dogEntity: DogEntity, databaseHandler: DatabaseHandler): Dog {
            val userEntity = databaseHandler.getUserByUsername(dogEntity.owner_username)
            val imageUrls = databaseHandler.getDogImagesById(dogEntity.id)

            return Dog(
                dogEntity.id,
                dogEntity.name,
                dogEntity.age,
                dogEntity.location,
                dogEntity.sex,
                dogEntity.weight,
                dogEntity.owner_username,
                userEntity?.name.toString(),
                userEntity?.phone.toString(),
                dogEntity.text,
                imageUrls.toTypedArray(),
                dogEntity.isFavorite
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
        SANTIAGO_DEL_ESTERO("Tierra del Fuego"),
        TIERRA_DEL_FUEGO("Tierra del Fuego"),
        TUCUMAN("Tucuman")
    }
}
