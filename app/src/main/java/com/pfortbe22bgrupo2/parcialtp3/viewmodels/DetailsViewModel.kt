package com.pfortbe22bgrupo2.parcialtp3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private lateinit var databaseHandler: DatabaseHandler

    // Método en tu ViewModel que realiza la operación de base de datos
    fun getDogImagesById(id: Int) {
        viewModelScope.launch {
            // Llama a tu método que accede a la base de datos dentro de un coroutine
            val dogImages = databaseHandler.getDogImagesById(id)
            // Realiza las operaciones con los datos obtenidos
            // Actualiza la UI con los datos, si es necesario
        }
    }
    private val selectedDog: MutableLiveData<Dog> = MutableLiveData()

    fun setSelectedDog(dog: Dog) {
        selectedDog.value = dog
    }

    fun getSelectedDog(): LiveData<Dog> {
        return selectedDog
    }

    fun adoptFromFavorites(id: Int) {
        val dog = databaseHandler.getAdoptionById(id)
        if (dog != null) {
            viewModelScope.launch {
                databaseHandler.insertAdoption(dog)
            }
        }
    }
}
