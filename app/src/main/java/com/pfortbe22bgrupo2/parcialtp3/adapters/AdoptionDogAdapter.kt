package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemDogBinding
import com.pfortbe22bgrupo2.parcialtp3.holders.FavoritesItemHolder
import com.pfortbe22bgrupo2.parcialtp3.listeners.AddToFavorite
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdoptionDogAdapter(
    private val context: Context,
    private var dogList: MutableList<Dog>,
    private val showAdoptionDetails: ShowAdoptionDetailsListener,
    private val addToFavorite: AddToFavorite,
    private val favorites: List<Int>,
    private val username: String
): RecyclerView.Adapter<FavoritesItemHolder>() {

    private lateinit var binding: ItemDogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemHolder {
        binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesItemHolder(binding)
    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: FavoritesItemHolder, position: Int) {
        holder.setName(dogList[position].name)
        holder.setImageUrl(dogList[position].image_urls?.get(0)!!, binding.root)
        holder.setDogAge(dogList[position].age.toString())
        holder.setDogBreed(dogList[position].breed)
        holder.setDogSubBreed(dogList[position].subbreed)
        holder.setDogSex(dogList[position].sex)
        holder.getCardLayout().setOnClickListener {
            showAdoptionDetails.onItemClickAction(position)
        }

        val isFavorite = favorites.contains(dogList[position].id)
        if (isFavorite) {
            holder.getFavIconImageView().setOnClickListener {
                onRemoveFavorite(position, holder)
            }
        }
        else {
            holder.getFavIconImageView().setOnClickListener {
                onAddFavorite(position, holder)
            }
        }

        holder.setFavoriteIcon(isFavorite)
    }

    fun updateData(favoriteDogs: MutableList<Dog>) {
        dogList = favoriteDogs.toMutableList()
        notifyDataSetChanged()
    }

    private fun onAddFavorite(position: Int, holder: FavoritesItemHolder) {
        addToFavorite.addFavorite(position)
        holder.setFavoriteIcon(true)
        holder.getFavIconImageView().setOnClickListener {
            onRemoveFavorite(position, holder)
        }
    }

    private fun onRemoveFavorite(position: Int, holder: FavoritesItemHolder) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.remove_from_favs_modal_title))
        builder.setMessage(context.getString(R.string.remove_from_favs_modal_text))
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            val dog = dogList[position]
            val databaseHandler = DatabaseHandler(context)
            CoroutineScope(Dispatchers.IO).launch {
                databaseHandler.deleteFavorite(username, dog.id)
                holder.setFavoriteIcon(false)
                Log.e("e", "REMOVE")
                holder.getFavIconImageView().setOnClickListener {
                    onAddFavorite(position, holder)
                }
            }
        }
        builder.setNegativeButton(R.string.no) { dialog, which ->
            Log.i("FavoritesAdapter", R.string.no.toString())
        }
        val dialog = builder.create()
        dialog.show()
    }
}