package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.data.DogsList
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentFavoritesBinding
import com.pfortbe22bgrupo2.parcialtp3.listeners.FavoritesItemClickListener

class FavoritesFragment : Fragment(), FavoritesItemClickListener {
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var favorites: DogsList = DogsList()

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
        favoritesAdapter = FavoritesAdapter(binding.root.context, favorites.dogList)
        linearLayoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.layoutManager = linearLayoutManager
        binding.favoritesRecyclerView.adapter = favoritesAdapter
    }

    override fun onFavoriteIconClick(position: Int) {

    }


}