package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.data.DogsList
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentAdoptedBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.AdoptedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdoptedFragment : Fragment() {
    private lateinit var viewModel: AdoptedViewModel
    private lateinit var binding: FragmentAdoptedBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adoptedDogs: MutableList<Dog>
    private var dogList: DogsList = DogsList()
    private var username = "????"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdoptedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AdoptedViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun initRecyclerView() {
        binding.adoptedRecyclerView.setHasFixedSize(false)
        try {
            favoritesAdapter = FavoritesAdapter(binding.root.context, dogList.dogList, null, null, username, false)
            linearLayoutManager = LinearLayoutManager(context)
            binding.adoptedRecyclerView.layoutManager = linearLayoutManager
            binding.adoptedRecyclerView.adapter = favoritesAdapter
        } catch (e: Exception) {
            Log.e("AdoptedFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    private fun loadData() {
        viewModel.loadAdoptedDogs()
        viewModel.adoptedDogs.observe(viewLifecycleOwner) { adoptedDogsList ->
            this.adoptedDogs = adoptedDogsList.toMutableList()
            if(adoptedDogs.isEmpty()) {
                binding.noElementsTextView.text = getString(R.string.no_elements_found)
            } else {
                binding.noElementsTextView.visibility = View.GONE
                initRecyclerView()
                favoritesAdapter.updateData(this.adoptedDogs)
            }
        }
    }
}
