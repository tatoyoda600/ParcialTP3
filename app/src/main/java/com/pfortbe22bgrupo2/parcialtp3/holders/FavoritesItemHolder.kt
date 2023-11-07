package com.pfortbe22bgrupo2.parcialtp3.holders

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemDogBinding

class FavoritesItemHolder(binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root) {

    private var binding: ItemDogBinding

    init {
        this.binding = binding
    }

    fun setName(name:String) {
        binding.nameTextViewId.text = name
    }

    fun setImageUrl(imageUrl : String,  context: View) {
        Glide.with(context).load(imageUrl).into(binding.itemDogImageViewId)
    }

    fun setDogAge(age: String) {
        binding.dogAgeTextViewId.text = """Edad: $age"""
    }

    fun setDogSex(sex: String) {
        binding.dogSexTextViewId.text = sex
    }

    fun setDogBreed(breed: String) {
        binding.dogBreedTextViewId.text = breed
    }

    fun setDogSubBreed(subBreed: String?) {
        binding.dogSubBreedTextViewId.text = subBreed
    }

    fun getCardLayout(): CardView {
        return binding.cardItemContainer
    }

    fun getFavIconImageView(): ImageView {
        return binding.favIconImageView
    }

    fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite){
            binding.favIconImageView.setImageResource(R.drawable.ic_add_to_fav_filled)
        }
        else {
            binding.favIconImageView.setImageResource(R.drawable.ic_add_to_fav)
        }
    }


}