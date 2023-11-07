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
class AdoptedViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
) : ViewModel() {
    val totalAdoptionsNumber = MutableLiveData<Int>()
    val adoptedDogs = MutableLiveData<List<Dog>>()

    // Cargar el número total de adopciones
    fun loadAdoptedListTotal(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalAdoptions = databaseHandler.getAdoptedDogListByUser(userName).size
                totalAdoptionsNumber.postValue(totalAdoptions)
            } catch (e: Exception) {
                Log.e("AdoptedViewModel", "Error al cargar el número total de adopciones: ${e.message}")
            }
        }
    }

    // Cargar la lista de perros adoptados
    fun loadAdoptedDogs(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = databaseHandler.getAdoptedDogListByUser(userName)
                adoptedDogs.postValue(list)
            } catch (e: Exception) {
                Log.e("AdoptedViewModel", "Error al cargar la lista de perros adoptados: ${e.message}")
            }
        }
    }
}
