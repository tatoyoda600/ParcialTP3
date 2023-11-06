package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.PublicationViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentPublicationBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Breeds
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.ApiHandler
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicationFragment : Fragment() {
    private lateinit var viewModel: PublicationViewModel
    lateinit var binding : FragmentPublicationBinding
    var breeds: Breeds? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublicationBinding.inflate(inflater, container, false)

        val locations = Dog.Location.values()
        val locationItems = mutableListOf<String>()
        for (l in locations) {
            locationItems.add(l.displayName)
        }
        val locationAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, locationItems.toTypedArray())
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationInput.setAdapter(locationAdapter)

        val apiHandler = ApiHandler()
        apiHandler.getBreeds { breedsResponse: Breeds ->
            if (breedsResponse.status == ApiHandler.SUCCESS_MSG) {
                breeds = breedsResponse
                val breedList = breedsResponse.message?.keys?.toTypedArray()
                if (breedList != null) {
                    val breedAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, breedList)
                    breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.breedInput.setAdapter(breedAdapter)
                    binding.breedContainer.isEnabled = true
                }
            }
        }

        binding.breedInput.setOnItemClickListener { parent: AdapterView<*>, arg1: View, position: Int, id: Long ->
            binding.subbreedInput.text = null
            val selected: String? = breeds?.message?.keys?.toList()?.get(id.toInt())
            if (selected != null) {
                val subBreeds = breeds?.message?.get(selected)
                val subBreedAdapter: ArrayAdapter<String>?
                if (subBreeds?.list != null && subBreeds.list.size > 0) {
                    subBreedAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, subBreeds.list)
                    binding.subbreedContainer.isEnabled = true
                }
                else {
                    subBreedAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, arrayOf<String>())
                    binding.subbreedContainer.isEnabled = false
                }
                subBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.subbreedInput.setAdapter(subBreedAdapter)
            }
        }

        binding.publicationSubmit.setOnClickListener { onSubmit() }

        return binding.root
    }

    private fun onSubmit() {
        val databaseHandler = DatabaseHandler(binding.root.context)

        val age = binding.dogAgeInput.text.toString().toIntOrNull()
        val weight = binding.weightInput.text.toString().toFloatOrNull()
        if (age != null && weight != null) {
            val name = binding.dogNameInput.text.toString()
            val location = binding.locationInput.text.toString()
            val sex = if (binding.sexInput.checkedRadioButtonId == binding.femaleInput.id) Dog.FEMALE else Dog.MALE
            val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
            val username = pref.getString("userName", "")?: ""
            val breed = binding.breedInput.text.toString()
            val subbreed = binding.subbreedInput.text.toString()
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
                        breed = breed,
                        subbreed = if (subbreed == "none") subbreed else null,
                        text = text,
                        image_urls = urls.toTypedArray()
                    ));

                    if (id != -1) {
                        // Put dog up for adoption successfully!
                        val dog = databaseHandler.getAdoptionById(id)
                        if (dog != null) {
                            withContext(Dispatchers.Main) {
                                val toast = Toast(context)
                                toast.setText(R.string.publish_adoption)
                                toast.show()
                            }
                            val fragment = DetailsFragment().apply {
                                arguments = Bundle().apply {
                                    putParcelable("dog", dog)
                                }
                            }
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.nav_host, fragment)
                                .commit()
                        }
                    }
                }
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PublicationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}