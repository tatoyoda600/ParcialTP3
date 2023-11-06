package com.pfortbe22bgrupo2.parcialtp3.viewmodels

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
    fun loadAdoptedListTotal()  {
        viewModelScope.launch(Dispatchers.IO) {
            val totalAdoptions = databaseHandler.getAdoptionList().size
            totalAdoptionsNumber.postValue(totalAdoptions)
        }
    }

    fun loadAdoptedDogs() {
        viewModelScope.launch(Dispatchers.IO) {
            val list =  databaseHandler.getAdoptionList()
            val dogList = mutableListOf<Dog>()
            for (dog in list) {
                dogList.add(dog)
            }
            adoptedDogs.postValue(dogList)
        }
    }
}