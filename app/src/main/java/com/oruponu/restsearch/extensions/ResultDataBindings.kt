package com.oruponu.restsearch.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oruponu.restsearch.R

@BindingAdapter("resultImageSource")
fun loadResultImage(view: ImageView, uri: String) {
    if (uri.isNotEmpty()) {
        Glide.with(view.context).load(uri).into(view)
    } else {
        view.setImageResource(R.drawable.ic_image_border)
    }
}

@BindingAdapter("resultImageSource")
fun setNoImageTextViewVisibility(view: TextView, uri: String) {
    if (uri.isNotEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("station", "stationExit", "walk")
fun setAccessText(view: TextView, station: String, stationExit: String, walk: String) {
    if (station.isNotEmpty()) {
        view.text = "$station$stationExit $walk" + "分"
    }
}
