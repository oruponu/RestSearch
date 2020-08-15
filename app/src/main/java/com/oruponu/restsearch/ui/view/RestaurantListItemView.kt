package com.oruponu.restsearch.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.extensions.getAccess
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class RestaurantListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, this)
    }

    fun setRestaurant(restaurant: Rest) {
        val shopImage1 = restaurant.imageUrl.shopImage1
        if (shopImage1.isNotEmpty()) {
            Glide.with(context).load(shopImage1).into(shopImageView)
            noImageTextView.visibility = View.GONE
        } else {
            shopImageView.setImageResource(R.drawable.ic_image_border)
            noImageTextView.visibility = View.VISIBLE
        }
        nameTextView.text = restaurant.name
        if (restaurant.access.station.isNotEmpty()) {
            accessTextView.text = restaurant.access.getAccess()
        }
        categoryTextView.text = restaurant.category
    }
}
