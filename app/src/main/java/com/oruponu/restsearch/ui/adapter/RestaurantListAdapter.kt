package com.oruponu.restsearch.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.ui.view.RestaurantListItemView

class RestaurantListAdapter(context: Context) :
    ArrayAdapter<Rest>(context, android.R.layout.simple_list_item_1) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        ((convertView as? RestaurantListItemView) ?: RestaurantListItemView(context)).apply {
            setRestaurant(getItem(position)!!)
        }
}
