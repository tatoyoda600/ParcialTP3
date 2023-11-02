package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable
import com.pfortbe22bgrupo2.parcialtp3.entities.DogEntity
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler

data class Dog(
    val id: Int,
    val name: String?,
    val age: Int,
    val location: String?,
    val sex: String?,
    val weight: Float,
    val owner_username: String?,
    val owner: String?,
    val phone: String?,
    val text: String?,
    val image_urls: Array<String>?
): Parcelable {
    constructor(
        name: String?,
        age: Int,
        location: String?,
        sex: String?,
        weight: Float,
        owner_username: String?,
        owner: String?,
        phone: String?,
        text: String?,
        image_urls: Array<String>?
    ): this(0, name, age, location, sex, weight, owner_username, owner, phone, text, image_urls)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray()
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
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toEntity(): DogEntity? {
        var output: DogEntity? = null

        if (name != null
            && location != null
            && sex != null
            && owner_username != null
            && text != null
        ) {
            if (id != 0) {
                output = DogEntity(id, name, age, location, sex, weight, owner_username, text)
            }
            else {
                output = DogEntity(name, age, location, sex, weight, owner_username, text)
            }
        }

        return output
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Dog> = object : Parcelable.Creator<Dog> {
            override fun createFromParcel(parcel: Parcel): Dog {
                return Dog(parcel)
            }

            override fun newArray(size: Int): Array<Dog?> {
                return arrayOfNulls(size)
            }
        }

        fun createFromEntity(dogEntity: DogEntity, databaseHandler: DatabaseHandler): Dog {
            val userEntity = databaseHandler.getUserByUsername(dogEntity.owner_username)!!
            val imageUrls = databaseHandler.getDogImagesById(dogEntity.id)

            return Dog(
                dogEntity.id,
                dogEntity.name,
                dogEntity.age,
                dogEntity.location,
                dogEntity.sex,
                dogEntity.weight,
                dogEntity.owner_username,
                userEntity.name,
                userEntity.phone,
                dogEntity.text,
                imageUrls.toTypedArray()
            )
        }
    }
}
