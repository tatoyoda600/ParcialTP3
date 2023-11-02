package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.LoginViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.signUpButton.setOnClickListener(){
            validateFields()
        }
    }

    private fun validateFields() {
        val name = binding.userNameEditText.text.toString()
        val cellphone = binding.cellphoneEditText.text.toString()
        if (!name.isEmpty() || !cellphone.isEmpty()) {
            if (cellphone.length == 10){
                val pref = activity?.getSharedPreferences("user",Context.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("name",name)
                editor?.putString("cellphone",cellphone)
                editor?.apply()
                //NAVEGAR AL HOME
            }else{
                binding.cellphoneEditText.error = "Telefono Invalido"
            }
        }else{
            binding.userNameEditText.error = "Campo Obligatorio"
            binding.cellphoneEditText.error = "Campo Obligatorio"
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}