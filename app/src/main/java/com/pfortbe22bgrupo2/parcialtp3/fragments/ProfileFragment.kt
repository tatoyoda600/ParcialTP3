package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.ProfileViewModel
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.uploadPictureButton.setOnClickListener {
            Glide.with(requireContext())
                .load("https://img.freepik.com/foto-gratis/adorable-perro-basenji-marron-blanco-sonriendo-dando-maximo-cinco-aislado-blanco_346278-1657.jpg?size=626&ext=jpg&ga=GA1.1.1412446893.1698796800&semt=sph")
                .into(binding.profileImageView)
        }
        setProfile()

    }

    private fun setProfile() {
        val pref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val name = pref?.getString("name","")
        val cellphone = pref?.getString("cellphone","")
        binding.nameTextView.text = "Nombre del Usuario: ${name}"
        binding.cellphoneTextView.text = "Telefono del Usuario: ${cellphone}"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}