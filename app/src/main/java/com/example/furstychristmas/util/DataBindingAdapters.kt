package com.example.furstychristmas.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


//STACKOVERFLOW <3
@BindingAdapter("icon")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
