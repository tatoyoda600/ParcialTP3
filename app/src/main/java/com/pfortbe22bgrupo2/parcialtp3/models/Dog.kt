package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable

data class Dog(
    val name: String?,
    val age: Int,
    val location: String?,
    val sex: String?,
    val weight: Float,
    val owner: String?,
    val phone: String?,
    val text: String?,
    val image_urls: Array<String>?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(location)
        parcel.writeString(sex)
        parcel.writeFloat(weight)
        parcel.writeString(owner)
        parcel.writeString(phone)
        parcel.writeString(text)
        parcel.writeStringArray(image_urls)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dog> {
        override fun createFromParcel(parcel: Parcel): Dog {
            return Dog(parcel)
        }

        override fun newArray(size: Int): Array<Dog?> {
            return arrayOfNulls(size)
        }
    }

}
