package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemDogBinding
import com.pfortbe22bgrupo2.parcialtp3.holders.FavoritesItemHolder
import com.pfortbe22bgrupo2.parcialtp3.listeners.AddToFavorite
import com.pfortbe22bgrupo2.parcialtp3.listeners.ShowAdoptionDetailsListener
import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class AdoptionDogAdapter(
    //private val context: Context,
    private var dogList: MutableList<Dog>,
    private val showAdoptionDetails: ShowAdoptionDetailsListener,
    private val addToFavorite: AddToFavorite
): RecyclerView.Adapter<FavoritesItemHolder>() {

    private lateinit var binding: ItemDogBinding
   // private val databaseHandler: DatabaseHandler = DatabaseHandler(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemHolder {
        binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesItemHolder(binding)
    }

    override fun getItemCount(): Int = dogList.size


    override fun onBindViewHolder(holder: FavoritesItemHolder, position: Int) {
        holder.setName(dogList[position].name!!)
        holder.setImageUrl(dogList[position].image_urls?.get(0)!!, binding.root)
        holder.setDogAge(dogList[position].age.toString())
        //holder.setDogBreed("dsada")
        //holder.setDogSubBreed("dasdsa")
        holder.setDogSex(dogList[position].sex.toString())
        holder.getCardLayout().setOnClickListener() {
            showAdoptionDetails.onItemClickAction(position)
        }
        holder.getFavIconImageView().setOnClickListener{
            addToFavorite.addFavorite(position)
        }
    }

    fun updateData(favoriteDogs: MutableList<Dog>) {
        dogList = favoriteDogs.toMutableList()
        notifyDataSetChanged()
    }

/*    private fun addToFavorite(position: Int) {
        val pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName","").toString()
        val dogId = dogList[position].id
        //val userFavoritesEntity = UserFavoritesEntity(userName,dogId)
        CoroutineScope(Dispatchers.IO).launch {
            //databaseHandler.userFavoritesDao.insertFavorite(userFavoritesEntity)
            databaseHandler.insertFavorite(userName,dogId)
        }
    }*/

}