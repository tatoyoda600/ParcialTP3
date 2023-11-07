package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.simulateHotReload
import androidx.recyclerview.widget.RecyclerView
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemDogBinding
import com.pfortbe22bgrupo2.parcialtp3.holders.FavoritesItemHolder
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel

class RecyclerAdapter(private val context: Context, private var dogList: MutableList<Dog>, private val showAdoptionDetails: ShowAdoptionDetailsListener?,
                      private val favoritesViewModel: FavoritesViewModel?,
                      private val username: String, showFavIcon: Boolean): RecyclerView.Adapter<FavoritesItemHolder>() {

    private lateinit var binding: ItemDogBinding
    private var showFavIcon = showFavIcon
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemHolder {
      binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return FavoritesItemHolder(binding)
    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: FavoritesItemHolder, position: Int) {
        holder.setName(dogList[position].name)
        holder.setImageUrl(dogList[position].image_urls?.get(0)!!, binding.root)
        if(showFavIcon) {
            holder.setDogAge(dogList[position].age.toString())
            holder.setDogSex(dogList[position].sex)
            holder.setDogBreed(dogList[position].breed)
            holder.setDogSubBreed(dogList[position].subbreed)
            holder.getCardLayout().setOnClickListener() {
                showAdoptionDetails?.onItemClickAction(position)
            }
            holder.getFavIconImageView().setOnClickListener {
                setOnRemoveItemAction(position)
            }
            holder.setFavoriteIcon(true)
        } else {
            holder.getFavIconImageView().visibility = View.INVISIBLE
        }

    }

    private fun setOnRemoveItemAction(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.remove_from_favs_modal_title))
        builder.setMessage(context.getString(R.string.remove_from_favs_modal_text))
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            val dog = dogList[position]
            favoritesViewModel?.deleteFavorite(username, dog.id)

            if (position == 0 && dogList.isNotEmpty()) {
                notifyItemChanged(0)
            } else if (position < dogList.size) {
                notifyItemRangeChanged(position, dogList.size - position)
            } else if (dogList.isEmpty()) {
                notifyDataSetChanged()
            }else {
                Log.e("FavoritesAdapter", R.string.error_message_removing_position_from_favorites.toString())
            }
        }
        builder.setNegativeButton(R.string.no) { dialog, which ->
            Log.i("FavoritesAdapter", R.string.no.toString())
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun updateData(favoriteDogs: MutableList<Dog>) {
        dogList = favoriteDogs.toMutableList()
        notifyDataSetChanged()
    }
}