package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.R

class ImageAdapter(var context: Context, var img_array: Array<String>?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var clickEvent: ((imageView: ImageView?, imagePath: String?) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(img_array?.get(position)).into(holder.imageView)
        holder.itemView.setOnClickListener { view: View ->
            clickEvent?.let {
                it(
                    holder.imageView,
                    img_array?.get(position)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return img_array?.size ?: 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.list_item_image)
        }
    }

    fun setOnItemClickListener(clickEvent: (imageView: ImageView?, imagePath: String?) -> Unit) {
        this.clickEvent = clickEvent
    }
}