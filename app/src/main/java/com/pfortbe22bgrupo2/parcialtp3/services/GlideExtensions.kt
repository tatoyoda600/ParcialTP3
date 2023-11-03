package com.pfortbe22bgrupo2.parcialtp3.services

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.material.carousel.MaskableFrameLayout
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.transition.Transition

fun RequestBuilder<Drawable>.into(maskableFrameLayout: MaskableFrameLayout) {
    val target = object : CustomTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            maskableFrameLayout.foreground = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            // En caso de que la carga de la imagen se cancele o se borre
        }
    }

    into(target)
}
