package com.oruponu.restsearch.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.oruponu.restsearch.R
import com.oruponu.restsearch.ui.adapter.ResultPagerAdapter
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity() {
    companion object {
        private const val TOTAL_PAGE_EXTRA = "total_page"
        private const val CATEGORIES_CODE_EXTRA = "categories_code"
        private const val LATITUDE_EXTRA = "latitude"
        private const val LONGITUDE_EXTRA = "longitude"
        private const val RANGE_EXTRA = "range"

        fun intent(
            context: Context,
            totalPage: Int,
            categoriesCode: String,
            latitude: Double,
            longitude: Double,
            range: Int
        ): Intent =
            Intent(context, ResultActivity::class.java).putExtra(TOTAL_PAGE_EXTRA, totalPage)
                .putExtra(CATEGORIES_CODE_EXTRA, categoriesCode).putExtra(LATITUDE_EXTRA, latitude)
                .putExtra(LONGITUDE_EXTRA, longitude).putExtra(RANGE_EXTRA, range)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val totalPage = intent.getIntExtra(TOTAL_PAGE_EXTRA, 1)
        val categoriesCode = intent.getStringExtra(CATEGORIES_CODE_EXTRA) ?: ""
        val latitude = intent.getDoubleExtra(LATITUDE_EXTRA, 0.0)
        val longitude = intent.getDoubleExtra(LONGITUDE_EXTRA, 0.0)
        val range = intent.getIntExtra(RANGE_EXTRA, 0)

        viewPager.adapter =
            ResultPagerAdapter(this, totalPage, categoriesCode, latitude, longitude, range)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (position + 1).toString()
        }.attach()
    }
}
