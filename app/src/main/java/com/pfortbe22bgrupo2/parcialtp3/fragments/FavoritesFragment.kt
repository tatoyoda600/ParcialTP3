package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.FavoritesAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentFavoritesBinding
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
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
    private lateinit var userName : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
         userName = pref.getString("userName","").toString()
         favoritesViewModel.loadFavoriteDogs(userName)
         favoritesViewModel.loadFavoriteDogs(userName)
         favoritesViewModel.favoriteDogs.observe(viewLifecycleOwner) { favoriteDogsList ->
             this.favoriteDogs = favoriteDogsList.toMutableList()
             Log.i("FavoritesFragment", "Getting favorites dog fot user: $userName")
             if(favoriteDogs.isEmpty()) {
                 binding.noElementsTextView.text = getString(R.string.no_elements_found_in_favorites)
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
            favoritesAdapter = FavoritesAdapter(binding.root.context, favoriteDogs, this, favoritesViewModel, userName, true)
            linearLayoutManager = LinearLayoutManager(context)
            binding.favoritesRecyclerView.layoutManager = linearLayoutManager
            binding.favoritesRecyclerView.adapter = favoritesAdapter
        } catch (e: Exception) {
            Log.e("FavoritesFragment", getString(R.string.error_initialization_recycler_view_failed, e.message))
        }
    }

    override fun onItemClickAction(position: Int) {
        val dog = favoriteDogs[position]
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
