package com.pfortbe22bgrupo2.parcialtp3.services

import com.pfortbe22bgrupo2.parcialtp3.models.Breeds
import com.pfortbe22bgrupo2.parcialtp3.models.BreedsResponse
import com.pfortbe22bgrupo2.parcialtp3.models.ImagesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("breeds/list/all")
    fun getBreeds() : Call<BreedsResponse>

    @GET("breed/{breed}/images/random/{count}")
    fun getBreedImages(@Path("breed") breed: String, @Path("count") count: Int) : Call<ImagesResponse>

    @GET("breed/{breed}/{subBreed}/images/random/{count}")
    fun getSubBreedImages(@Path("breed") breed: String, @Path("subBreed") subBreed: String, @Path("count") count: Int) : Call<ImagesResponse>
}