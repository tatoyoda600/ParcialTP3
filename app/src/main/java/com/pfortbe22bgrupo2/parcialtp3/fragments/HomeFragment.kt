package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
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
        homeViewModel.addDogToFavorites(userName, dogId)
        Log.i("HomeFragment", "Adding dog: ${dogList[position].name} to favorites of user: $userName")

    }
}