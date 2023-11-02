package com.pfortbe22bgrupo2.parcialtp3.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pfortbe22bgrupo2.parcialtp3.R

class ImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        val imageView = findViewById<ImageView>(R.id.imageView)
        Glide.with(this@ImageViewActivity).load(intent.getStringExtra("image")).into(imageView)
    }
}