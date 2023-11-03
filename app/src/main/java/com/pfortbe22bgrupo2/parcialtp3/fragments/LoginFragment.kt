package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.LoginViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentLoginBinding
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.signUpButton.setOnClickListener(){
            validateFields()
        }
    }

    private fun validateFields() {
        val userName = binding.userNameEditText.text.toString()
        val name = binding.nameEditTExt.text.toString()
        val cellphone = binding.cellphoneEditText.text.toString()

        val userNameValid = userName.length > 3
        val nameValid = name.length > 3
        val cellphoneValid = cellphone.length == 10

        if (!userNameValid) {
            binding.userNameEditText.error = "Nombre de Usuario muy corto"
        }
        if (!nameValid) {
            binding.nameEditTExt.error = "Nombre muy corto"
        }
        if (!cellphoneValid) {
            binding.cellphoneEditText.error = "Teléfono Inválido"
        }
        if (userNameValid && nameValid && cellphoneValid) {
            // con viewModel NO lo termina creando al usuario
            //viewModel.addUser(userName, name, cellphone)

            val databaseHandler: DatabaseHandler = DatabaseHandler(binding.root.context)
            CoroutineScope(Dispatchers.IO).launch {
                val user: UserEntity = UserEntity(userName, name, cellphone, "")
                databaseHandler.insertUser(user)
            }
            saveUserNameInSharedPreferences()
            navToMainActivity()
        }
    }

    private fun saveUserNameInSharedPreferences() {
        val userName = binding.userNameEditText.text.toString()
        val pref = activity?.getSharedPreferences("user",Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("userName", userName)
        editor?.apply()
    }

    private fun navToMainActivity() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        findNavController().navigate(action)
    }

}