package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable
import android.util.ArrayMap

data class BreedsResponse(
    val status: String?,
    val message: ArrayMap<String, SubBreeds>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayMap<SubBreeds>(SubBreeds.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeTypedArrayMap(message, 0)
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
