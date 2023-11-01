package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable
import android.util.ArrayMap

data class BreedsResponse(
    val message: ArrayMap<String, SubBreeds>?,
    val status: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayMap<SubBreeds>(SubBreeds.CREATOR),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedArrayMap(message, 0)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BreedsResponse> {
        override fun createFromParcel(parcel: Parcel): BreedsResponse {
            return BreedsResponse(parcel)
        }

        override fun newArray(size: Int): Array<BreedsResponse?> {
            return arrayOfNulls(size)
        }
    }
}
