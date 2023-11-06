package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemDogBinding
import com.pfortbe22bgrupo2.parcialtp3.holders.FavoritesItemHolder
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel

class FavoritesAdapter(private val context: Context, private var dogList: MutableList<Dog>, private val showAdoptionDetails: ShowAdoptionDetailsListener?,
                       private val favoritesViewModel: FavoritesViewModel?,
                       private val username: String, private val showAllFields: Boolean): RecyclerView.Adapter<FavoritesItemHolder>() {

    private lateinit var binding: ItemDogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemHolder {
      binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return FavoritesItemHolder(binding)
    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: FavoritesItemHolder, position: Int) {
        if(showAllFields){
        holder.setName(dogList[position].name!!)
        holder.setImageUrl(dogList[position].image_urls?.get(0)!!, binding.root)
        holder.setDogAge(dogList[position].age.toString())
        //holder.setDogBreed("dsada")
        //holder.setDogSubBreed("dasdsa")
        holder.setDogSex(dogList[position].sex.toString())
        holder.getCardLayout().setOnClickListener() {
            showAdoptionDetails?.onItemClickAction(position)
        }
        holder.getSaveButtonItem().setOnClickListener{
            setOnRemoveItemAction(position)
        }
        } else {
            holder.setName(dogList[position].name!!)
            holder.setImageUrl(dogList[position].image_urls?.get(0)!!, binding.root)
            holder.getSaveButtonItem().visibility = View.INVISIBLE
        }
    }

    private fun setOnRemoveItemAction(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.remove_from_favs_modal_title))
        builder.setMessage(context.getString(R.string.remove_from_favs_modal_text))
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            val dog = dogList[position]
            favoritesViewModel?.deleteFavorite(username, dog.id) //esto es algo que debería hacer el adapter?? consultar
            dogList.removeAt(position) //es necesario? probar

            // notifico la eliminación del elemento en la posición 'position'
            notifyItemRemoved(position)

            // desplazamiento de elementos
            if (position == 0 && dogList.isNotEmpty()) {
                // Si se eliminó el primer elemento y quedan elementos en la lista, notifica la eliminación de la nueva posición 0.
                notifyItemChanged(0)
            } else if (position < dogList.size) {
                // Si eliminaste un elemento diferente al primero y no era el último elemento, notifica los cambios para el desplazamiento.
                notifyItemRangeChanged(position, dogList.size - position)
            } else if (dogList.isEmpty()) {
                // se notifica la eliminación de toda la lista
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