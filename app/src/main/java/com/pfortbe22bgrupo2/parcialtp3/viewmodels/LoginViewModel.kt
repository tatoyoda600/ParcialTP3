package com.pfortbe22bgrupo2.parcialtp3.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler
) : ViewModel() {

    fun addUser(userName:String,name:String,cellphone:String){
        viewModelScope.launch {
            val newUser = UserEntity(userName,name,cellphone,"")
            databaseHandler.insertUser(newUser)
        }
    }
}