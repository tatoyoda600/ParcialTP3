package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.PublicationViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentPublicationBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

class PublicationFragment : Fragment() {
    private lateinit var viewModel: PublicationViewModel
    lateinit var binding : FragmentPublicationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublicationBinding.inflate(inflater, container, false)

        binding.publicationSubmit.setOnClickListener {
            val databaseHandler = DatabaseHandler(binding.root.context)

            val age = binding.dogAgeInput.text.toString().toIntOrNull()
            val weight = binding.weightInput.text.toString().toFloatOrNull()
            if (age != null && weight != null) {
                val name = binding.dogNameInput.text.toString()
                val location = binding.locationInput.text.toString()
                val sex = if (binding.sexInput.checkedRadioButtonId == binding.femaleInput.id) Dog.FEMALE else Dog.MALE
                //TODO("HOW DO YOU GET THE USERNAME???")
                val username = "????"
                val text = binding.textInput.text.toString()
                val urls = binding.urlsInput.text.toString().split("\n", ", ", ",", "; ", ";", " ").toMutableList()
                urls.removeAll { str: String -> str.isBlank() }

                CoroutineScope(Dispatchers.IO).launch {
                    val user = databaseHandler.getUserByUsername(username)
                    if (user != null) {
                        val id = databaseHandler.insertAdoption(Dog(
                            name = name,
                            age = age,
                            location = location,
                            sex = sex,
                            weight = weight,
                            owner_username = username,
                            owner = user.name,
                            phone = user.phone,
                            text = text,
                            image_urls = urls.toTypedArray()
                        ));

                        if (id != -1) {
                            // Put dog up for adoption successfully!
                            val dog = databaseHandler.getAdoptionById(id)
                            if (dog != null) {
                                binding.root.findNavController()
                                    .navigate(
                                        PublicationFragmentDirections
                                            .actionPublicationFragmentToDetailsFragment(dog)
                                    );
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PublicationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}