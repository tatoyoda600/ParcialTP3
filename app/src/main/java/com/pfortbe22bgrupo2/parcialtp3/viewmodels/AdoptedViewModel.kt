package com.pfortbe22bgrupo2.parcialtp3.viewmodels

import android.util.Log
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

    fun loadAdoptedListTotal(userName : String)  {
        viewModelScope.launch(Dispatchers.IO) {
            val totalAdoptions = databaseHandler.getAdoptedDogListByUser(userName).size
            totalAdoptionsNumber.postValue(totalAdoptions)
        }
    }

    fun loadAdoptedDogs(userName : String){
        viewModelScope.launch(Dispatchers.IO) {
            val list =  databaseHandler.getAdoptedDogListByUser(userName)
            val dogList = mutableListOf<Dog>()
            if(list.isNotEmpty()){
                for (dog in list) {
                    dogList.add(dog)
                }
            } else {
               Log.i("AdoptedViewModel", "No adopted dogs found")
            }
            adoptedDogs.postValue(dogList)
        }
    }
}