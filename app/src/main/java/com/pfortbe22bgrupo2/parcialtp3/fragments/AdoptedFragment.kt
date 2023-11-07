package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.RecyclerAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentAdoptedBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.AdoptedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdoptedFragment : Fragment() {
    private lateinit var viewModel: AdoptedViewModel
    private lateinit var binding: FragmentAdoptedBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adoptedDogs: MutableList<Dog>
    private lateinit var userName : String

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
            recyclerAdapter = RecyclerAdapter(binding.root.context, adoptedDogs, null, null, userName, false)
            linearLayoutManager = LinearLayoutManager(context)
            binding.adoptedRecyclerView.layoutManager = linearLayoutManager
            binding.adoptedRecyclerView.adapter = recyclerAdapter
        } catch (e: Exception) {
            Log.e("AdoptedFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        userName = pref.getString("userName","").toString()
        viewModel.loadAdoptedDogs(userName)
        viewModel.adoptedDogs.observe(viewLifecycleOwner) { adoptedDogsList ->
            this.adoptedDogs = adoptedDogsList.toMutableList()
            Log.i("AdoptedFragment", "Getting adopted dogs for user: $userName")
            if(adoptedDogs.isEmpty()) {
                binding.noElementsTextView.text = getString(R.string.no_elements_found_in_adopted)
            } else {
                binding.noElementsTextView.visibility = View.GONE
                initRecyclerView()
                recyclerAdapter.updateData(this.adoptedDogs)
            }
        }
    }
}