package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.LoginActivity
import com.pfortbe22bgrupo2.parcialtp3.databinding.DialogUrlInputBinding
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.ProfileViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userName: String
    private lateinit var pref: SharedPreferences
    private lateinit var inputBinding: DialogUrlInputBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        inputBinding = DialogUrlInputBinding.inflate(inflater,container,false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        userName = pref.getString("userName","").toString()
        getProfileImage(userName)
        return binding.root
    }

    private fun getProfileImage(userName: String) {
        val url = profileViewModel.getUserProfileImage(userName)
        setImage(url)

    }

    override fun onStart() {
        super.onStart()
        setProfile(userName)

        binding.uploadPictureButton.setOnClickListener {
            showUrlInputDialog()
        }

        binding.deleteUserButton.setOnClickListener(){
            showConfirmationDialog()
        }
    }
    private fun showUrlInputDialog() {
/*        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_url_input, null)
        val imageUrl = dialogView.findViewById<EditText>(R.id.url_image_edit_text).text.toString()*/
        // NO ME CARGA LA IMAGEN DE UNA- TENGO QUE APRETAR DOS VECES EL BOTON
        val dialogView = inputBinding.root
        val imageUrl = inputBinding.urlImageEditText.text.toString()
        if (dialogView.parent != null) {
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Ingresar URL de la Foto")
            .setView(dialogView)
            .setPositiveButton("Subir") { dialog, which ->
                if (imageUrl.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                profileViewModel.addProfileImageToUser(imageUrl, userName)
                                binding.profileImageView.setImageDrawable(resource)
                                return false
                            }

                        })
                        .into(binding.profileImageView)
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        alertDialog.show()
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
        profileViewModel.deleteUser(userName)
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
        profileViewModel.setCurrentUserData(userName)
        val USUARIO = "Usuario: "

        profileViewModel.currentUser.observe(this, Observer {
            binding.userNameTextView.text = USUARIO + userName
            binding.nameTextView.text = "Nombre: ${ it?.name }"
            binding.cellphoneTextView.text = "Telefono: ${it?.phone}"
        })
    }

    private fun setImage(imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.profileImageView)
    }


}