package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.DetailsActivity
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

        return binding.root
        
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
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
            adoptionDogAdapter = AdoptionDogAdapter(dogList, this, this)
            val linearLayoutManager = LinearLayoutManager(context)
            binding.homeRecyclerView.layoutManager = linearLayoutManager
            binding.homeRecyclerView.adapter = adoptionDogAdapter
        } catch (e: Exception) {
            Log.e("FavoritesFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    override fun onItemClickAction(position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("dog", dogList[position])
        startActivity(intent)
    }

    override fun addFavorite(position: Int) {
        val databaseHandler = DatabaseHandler(binding.root.context)
        val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName","").toString()
        val dogId = dogList[position].id

        CoroutineScope(Dispatchers.IO).launch {
            databaseHandler.insertFavorite(userName,dogId)
            Log.d("MENSAJE DE AGREGADO A FAVORITO", "SE DEBERIA AGREGAR A FAVORITOS")

        }
    }
}