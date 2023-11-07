package com.pfortbe22bgrupo2.parcialtp3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
): ViewModel() {

    private val selectedDog: MutableLiveData<Dog> = MutableLiveData()

    // Método en tu ViewModel que realiza la operación de base de datos
    fun getDogImagesById(id: Int) {
        viewModelScope.launch {
            // Llama a tu método que accede a la base de datos dentro de un coroutine
            val dogImages = databaseHandler.getDogImagesById(id)
            // Realiza las operaciones con los datos obtenidos
            // Actualiza la UI con los datos, si es necesario
        }
    }

    fun setSelectedDog(dog: Dog) {
        selectedDog.value = dog
    }

    fun getSelectedDog(): LiveData<Dog> {
        return selectedDog
    }

    fun adoptFromFavorites(dog: Dog, userName : String) {
        viewModelScope.launch(Dispatchers.IO){
            val user = databaseHandler.getUserByUsername(userName)
            if(user != null){
                databaseHandler.adoptDog(userName, dog.id)
                Log.i("DetailsViewModel", "Adopted dog: ${dog.id}")
            } else {
                Log.i("DetailsViewModel", "User not found or null")
            }
        }
    }
}