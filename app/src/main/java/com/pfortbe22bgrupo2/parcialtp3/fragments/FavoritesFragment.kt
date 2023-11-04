package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.activities.DetailsActivity
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.data.DogsList
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentFavoritesBinding
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel


class FavoritesFragment : Fragment(), ShowAdoptionDetailsListener {
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var favorites: DogsList = DogsList()
    //private lateinit var auth: FirebaseAuth
    private lateinit var navHost: NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.favoritesRecyclerView.setHasFixedSize(false)
        favoritesAdapter = FavoritesAdapter(binding.root.context, favorites.dogList, this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.layoutManager = linearLayoutManager
        binding.favoritesRecyclerView.adapter = favoritesAdapter
    }

    override fun onItemClickAction(position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("dog", favorites.dogList[position])
        startActivity(intent)
    }

}
