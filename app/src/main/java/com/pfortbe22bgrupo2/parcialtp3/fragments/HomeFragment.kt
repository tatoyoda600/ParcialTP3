package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.AdoptionDogAdapter
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.HomeViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentHomeBinding
import com.pfortbe22bgrupo2.parcialtp3.listeners.AddToFavorite
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment(), ShowAdoptionDetailsListener, AddToFavorite {
    private lateinit var homeViewModel: HomeViewModel
    lateinit var binding : FragmentHomeBinding
    private var dogList: MutableList<Dog> = mutableListOf()
    private lateinit var adoptionDogAdapter: AdoptionDogAdapter
    private lateinit var dogs: MutableList<Dog>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val databaseHandler = DatabaseHandler(binding.root.context)
        val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName","").toString()
        val thisFragment = this
        CoroutineScope(Dispatchers.IO).launch {
            val favorites = databaseHandler.getFavoriteIDsByUsername(userName)
            adoptionDogAdapter = AdoptionDogAdapter(dogList, thisFragment, thisFragment, favorites)
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val listNames: MutableList<String> = mutableListOf()
            if(!checkedIds.isEmpty()){
                dogs = mutableListOf()
                for (chipId in checkedIds){
                    val chipName = binding.chipGroup.findViewById<Chip>(chipId).text.toString()
                    listNames.add(chipName)
                }
            }
            chipFilter(listNames)
            //else{
//                for (chipId in checkedIds){
//                    val chipName = binding.chipGroup.findViewById<Chip>(chipId).text.toString()
//                    chipFilter(chipName)
//                }
//            }
        }
        return binding.root
        
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilter()
        loadData()
    }

    private fun initFilter() {
        binding.searchEditText.addTextChangedListener { homeFilter ->
            var dogsFiltered = dogList.filter { dog ->
                if (dog.subbreed == null){
                    dog.breed.lowercase().contains(homeFilter.toString().lowercase())
                }else{
                    dog.breed.lowercase().contains(homeFilter.toString().lowercase()) ||
                    dog.subbreed.lowercase().contains(homeFilter.toString().lowercase())}
                }
            dogsFiltered = dogsFiltered.toMutableList()
            adoptionDogAdapter.updateData(dogsFiltered)
        }
    }

    private fun chipFilter(chipNames: List<String>) {
        if(!chipNames.isEmpty()){
            for (chipN in chipNames){
                val dogsFiltered = dogList.filter { dog ->
                    dog.breed.lowercase().contains(chipN.lowercase())
                }
                dogs.addAll(dogsFiltered)
            }
            adoptionDogAdapter.updateData(dogs)
            }
        else{
            adoptionDogAdapter.updateData(dogList)
        }
    }

    private fun loadData() {
        homeViewModel.loadDogs()
        homeViewModel.dogList.observe(viewLifecycleOwner) { dogList ->
            this.dogList = dogList.toMutableList()
            if(!dogList.isEmpty()) {
                initRecyclerView()
                adoptionDogAdapter.updateData(this.dogList)
            }
        }
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.setHasFixedSize(false)
        try {
            //adoptionDogAdapter = AdoptionDogAdapter(dogList, this, this)
            val linearLayoutManager = LinearLayoutManager(context)
            binding.homeRecyclerView.layoutManager = linearLayoutManager
            binding.homeRecyclerView.adapter = adoptionDogAdapter
        } catch (e: Exception) {
            Log.e("FavoritesFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    override fun onItemClickAction(position: Int) {
        val dog = dogList[position]
        val fragment = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("dog", dog)
            }
        }
        Log.i("HomeFragment", "Showing details of dog: ${dog.name}")
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, fragment)
            .commit()

    }

    override fun addFavorite(position: Int) {
        val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName","").toString()
        val dogId = dogList[position].id


//        //CHANGE BETWEEN FILLED AND OUTLINED FAV ICON IMAGE
//        val image = binding.root.findViewById<ImageView>(R.id.fav_icon_image_view)
//        image.isActivated = !image.isActivated

//        CoroutineScope(Dispatchers.IO).launch {
//            databaseHandler.insertFavorite(userName,dogId)
//            Log.d("MENSAJE DE AGREGADO A FAVORITO", "SE DEBERIA AGREGAR A FAVORITOS")
//            Log.i("HomeFragment", "Adding dog: ${dogList[position].name} to favorites of user: $userName")
//        }

        homeViewModel.addDogToFavorites(userName, dogId)
        Log.i("HomeFragment", "Adding dog: ${dogList[position].name} to favorites of user: $userName")

    }
}