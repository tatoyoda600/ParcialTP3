package com.pfortbe22bgrupo2.parcialtp3.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
): ViewModel() {

    val currentUser = MutableLiveData<UserEntity?>()

    fun setCurrentUserData(userName: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                databaseHandler.getUserByUsername(userName)
            }
            currentUser.postValue(result)
        }
    }

    fun deleteUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = databaseHandler.getUserByUsername(userName)
            databaseHandler.deleteUser(result!!)
        }
    }

}