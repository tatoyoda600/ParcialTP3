package com.pfortbe22bgrupo2.parcialtp3.models

import android.util.ArrayMap

data class BreedsResponse(
    val message: ArrayMap<String, Array<String>>?,
    val status: String?
) {
    fun toBreeds(): Breeds {
        val map = ArrayMap<String, SubBreeds>()
        for (k in message?.keys?: setOf()) {
            map.put(k, SubBreeds(message?.get(k)))
        }
        return Breeds(map, status)
    }
}