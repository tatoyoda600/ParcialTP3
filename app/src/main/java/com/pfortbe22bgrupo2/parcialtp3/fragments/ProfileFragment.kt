package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.activities.LoginActivity
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.ProfileViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userName: String
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        userName = pref.getString("userName","").toString()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setProfile(userName)

        binding.uploadPictureButton.setOnClickListener {
            Glide.with(requireContext())
                .load("https://img.freepik.com/foto-gratis/adorable-perro-basenji-marron-blanco-sonriendo-dando-maximo-cinco-aislado-blanco_346278-1657.jpg?size=626&ext=jpg&ga=GA1.1.1412446893.1698796800&semt=sph")
                .into(binding.profileImageView)
        }

        binding.deleteUserButton.setOnClickListener(){
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas eliminar tu cuenta de usuario?")
        builder.setPositiveButton("Sí") { dialog, which ->
            deleteUser()
        }
        builder.create().show()
    }

    private fun deleteUser() {
        viewModel.deleteUser(userName)
        val editor = pref.edit()
        editor?.remove("userName")
        editor?.apply()
        navToLogin()
    }

    private fun navToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setProfile(userName: String) {
        viewModel.setCurrentUserData(userName)
        val USUARIO = "Usuario: "

        viewModel.currentUser.observe(this, Observer {
            binding.userNameTextView.text = USUARIO + userName
            binding.nameTextView.text = "Nombre: ${ it?.name }"
            binding.cellphoneTextView.text = "Telefono: ${it?.phone}"
        })
    }


}