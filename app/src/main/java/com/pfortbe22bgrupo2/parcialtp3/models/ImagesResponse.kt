package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable

data class ImagesResponse(
    val message: Array<String>?,
    val status: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArray(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(message)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagesResponse> {
        override fun createFromParcel(parcel: Parcel): ImagesResponse {
            return ImagesResponse(parcel)
        }

        override fun newArray(size: Int): Array<ImagesResponse?> {
            return arrayOfNulls(size)
        }
    }

}
