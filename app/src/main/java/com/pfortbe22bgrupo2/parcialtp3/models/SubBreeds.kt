package com.pfortbe22bgrupo2.parcialtp3.models

import android.os.Parcel
import android.os.Parcelable

data class SubBreeds(
    val list: Array<String>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArray()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubBreeds> {
        override fun createFromParcel(parcel: Parcel): SubBreeds {
            return SubBreeds(parcel)
        }

        override fun newArray(size: Int): Array<SubBreeds?> {
            return arrayOfNulls(size)
        }
    }

}
