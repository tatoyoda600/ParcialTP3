package com.pfortbe22bgrupo2.parcialtp3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.R

class ImageAdapter(var context: Context, var arrayList: ArrayList<String>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var setItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(arrayList[position]).into(holder.imageView)
        holder.itemView.setOnClickListener { view: View? ->
            setItemClickListener!!.onClick(
                holder.imageView,
                arrayList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.list_item_image)
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.setItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(imageView: ImageView?, path: String?)
    }
}