package com.pfortbe22bgrupo2.parcialtp3.utilities

import android.util.ArrayMap
import android.util.Log
import com.pfortbe22bgrupo2.parcialtp3.models.Breeds
import com.pfortbe22bgrupo2.parcialtp3.models.BreedsResponse
import com.pfortbe22bgrupo2.parcialtp3.models.ImagesResponse
import com.pfortbe22bgrupo2.parcialtp3.services.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHandler {
    val retrofit: ApiInterface

    companion object {
        const val BASE_URL: String = "https://dog.ceo/api/"
        const val FAIL_MSG: String = "failure"
        const val SUCCESS_MSG: String = "success"
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun getBreeds(callback: (Breeds) -> Unit) {
        var data = retrofit.getBreeds()
        data.enqueue(object : Callback<BreedsResponse> {
            override fun onResponse(call: Call<BreedsResponse>, response: Response<BreedsResponse>) {
                val body = response.body()
                if (body != null) {
                    callback(body.toBreeds())
                }
                else {
                    callback(Breeds(ArrayMap(), FAIL_MSG))
                }
            }

            override fun onFailure(call: Call<BreedsResponse>, t: Throwable) {
                Log.e("API", t.message.toString())
                TODO("Not yet implemented")
            }
        })
    }

    fun getBreedImages(breed: String, count: Int, callback: (ImagesResponse) -> Unit) {
        var data = retrofit.getBreedImages(breed, count)
        data.enqueue(object : Callback<ImagesResponse> {
            override fun onResponse(call: Call<ImagesResponse>, response: Response<ImagesResponse>) {
                val body = response.body()
                if (body != null) {
                    callback(body)
                }
                else {
                    callback(ImagesResponse(arrayOf(), FAIL_MSG))
                }
            }

            override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getSubBreedImages(breed: String, subBreed: String, count: Int, callback: (ImagesResponse) -> Unit) {
        var data = retrofit.getSubBreedImages(breed, subBreed, count)
        data.enqueue(object : Callback<ImagesResponse> {
            override fun onResponse(call: Call<ImagesResponse>, response: Response<ImagesResponse>) {
                val body = response.body()
                if (body != null) {
                    callback(body)
                }
                else {
                    callback(ImagesResponse(arrayOf(), FAIL_MSG))
                }
            }

            override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}