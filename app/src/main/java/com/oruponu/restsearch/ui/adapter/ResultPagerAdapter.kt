package com.oruponu.restsearch.ui.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oruponu.restsearch.ui.view.RestaurantListFragment

class ResultPagerAdapter(
    fragment: FragmentActivity,
    private val totalPage: Int,
    private val categoriesCode: String,
    private val latitude: Double,
    private val longitude: Double,
    private val range: Int
) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int) = RestaurantListFragment.createInstance(
        position,
        categoriesCode,
        latitude,
        longitude,
        range
    )

    override fun getItemCount(): Int = totalPage
}
