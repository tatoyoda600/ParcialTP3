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
class FavoritesViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
) : ViewModel() {
    val favoriteDogs = MutableLiveData<List<Dog>>()

    fun loadFavoriteDogs(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val idList = databaseHandler.getFavoriteIDsByUsername(username)
            val dogList = mutableListOf<Dog>()
            for (id in idList) {
                val dog = databaseHandler.getAdoptionById(id)
                dog?.let { dogList.add(dog) }
            }
            favoriteDogs.postValue(dogList)
        }
    }

    fun deleteFavorite(userName: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseHandler.deleteFavorite(userName, id)
            loadFavoriteDogs(userName)
        }
    }
}
