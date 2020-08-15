package com.oruponu.restsearch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.databinding.RestaurantListItemBinding

class RestaurantListAdapter(context: Context) :
    ArrayAdapter<Rest>(context, android.R.layout.simple_list_item_1) {
    private val inflater = LayoutInflater.from(context)
    private lateinit var binding: RestaurantListItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        when (convertView) {
            null -> {
                binding =
                    DataBindingUtil.inflate(inflater, R.layout.restaurant_list_item, parent, false)
                binding.root.tag = binding
            }
            else -> {
                binding = convertView.tag as RestaurantListItemBinding
            }
        }

        val rest = this.getItem(position)
        if (rest != null) {
            binding.rest = rest
        }

        return binding.root
    }
}
