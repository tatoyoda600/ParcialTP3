package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.DetailsActivity
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentFavoritesBinding
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(), ShowAdoptionDetailsListener {
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var favoriteDogs: MutableList<Dog>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

     private fun loadData() {

         val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
         val userName = pref.getString("userName","").toString()

         favoritesViewModel.loadFavoriteDogs(userName)
         favoritesViewModel.favoriteDogs.observe(viewLifecycleOwner) { favoriteDogsList ->
             this.favoriteDogs = favoriteDogsList.toMutableList()
             if(favoriteDogs.isEmpty()) {
                 binding.noElementsTextView.text = getString(R.string.no_elements_found)
             } else {
                 binding.noElementsTextView.visibility = View.GONE
                 initRecyclerView()
                 favoritesAdapter.updateData(this.favoriteDogs)
             }
         }
    }

    private fun initRecyclerView() {
        binding.favoritesRecyclerView.setHasFixedSize(false)
        try {
            favoritesAdapter = FavoritesAdapter(binding.root.context, favoriteDogs, this)
            linearLayoutManager = LinearLayoutManager(context)
            binding.favoritesRecyclerView.layoutManager = linearLayoutManager
            binding.favoritesRecyclerView.adapter = favoritesAdapter
        } catch (e: Exception) {
            Log.e("FavoritesFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    override fun onItemClickAction(position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("dog", favoriteDogs[position])
        startActivity(intent)
    }

}
