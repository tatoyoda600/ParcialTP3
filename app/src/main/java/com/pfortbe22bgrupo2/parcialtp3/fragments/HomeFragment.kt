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
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.data.DogsList
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.HomeViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentHomeBinding
import com.pfortbe22bgrupo2.parcialtp3.entities.UserFavoritesEntity
import com.pfortbe22bgrupo2.parcialtp3.listeners.AddToFavorite
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

class HomeFragment: Fragment(), ShowAdoptionDetailsListener, AddToFavorite {
    private lateinit var homeViewModel: HomeViewModel
    lateinit var binding : FragmentHomeBinding
    private var dogList: MutableList<Dog> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dogList = DogsList().dogList
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
        
    }

    //VER PORQUE NO LO CARGA DE UNA
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

/*    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }*/

        private fun initRecyclerView() {
            binding.homeRecyclerView.setHasFixedSize(false)
            try {
                val adoptionDogAdapter = AdoptionDogAdapter(dogList, this, this)
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