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
class HomeViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
) : ViewModel() {
    val dogList = MutableLiveData<List<Dog>>()

    fun loadDogs() {
        viewModelScope.launch(Dispatchers.IO) {
            dogList.postValue(databaseHandler.getAdoptionList())
        }
    }
}