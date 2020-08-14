package com.oruponu.restsearch.extensions

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.util.LinkifyCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.oruponu.restsearch.R
import com.oruponu.restsearch.databinding.CategoryDetailBinding

@BindingAdapter("category")
fun setCategoryChip(flexboxLayout: FlexboxLayout, categoryNameS: List<String>) {
    categoryNameS.forEach {
        if (it.isEmpty()) {
            return
        }
        val categoryItem = DataBindingUtil.inflate<CategoryDetailBinding>(
            LayoutInflater.from(flexboxLayout.context),
            R.layout.category_detail,
            flexboxLayout,
            true
        )
        categoryItem.chip.text = it
    }
}

@BindingAdapter("address", "latitude", "longitude")
fun setAddressLink(view: TextView, address: String, latitude: String, longitude: String) {
    val spannableString = SpannableString(address)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            view.context.startActivity(intent)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)

            ds.isUnderlineText = true
        }
    }

    spannableString.setSpan(clickableSpan, 0, address.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = spannableString
    view.movementMethod = LinkMovementMethod.getInstance()
    view.highlightColor = Color.TRANSPARENT
}

@BindingAdapter("tel")
fun setTelLink(view: TextView, tel: String) {
    view.text = tel
    LinkifyCompat.addLinks(
        view,
        Patterns.PHONE,
        "tel:",
        Linkify.sPhoneNumberMatchFilter,
        Linkify.sPhoneNumberTransformFilter
    )
}

@BindingAdapter("imageSource")
fun loadImage(view: ImageView, uri: String?) {
    if (uri.isNullOrEmpty()) {
        view.visibility = View.GONE
        return
    }
    Glide.with(view.context).load(uri).into(view)
}

@BindingAdapter("latitude", "longitude")
fun startNavigation(view: View, latitude: String, longitude: String) {
    view.setOnClickListener {
        val uri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        view.context.startActivity(intent)
    }
}
