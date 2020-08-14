package com.oruponu.restsearch.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.ui.adapter.RestaurantListAdapter
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity() {
    companion object {
        private const val REST_LIST_EXTRA = "rest_list"

        fun intent(context: Context, restList: ArrayList<Rest>): Intent =
            Intent(context, ResultActivity::class.java).putExtra(REST_LIST_EXTRA, restList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val listAdapter =
            RestaurantListAdapter(
                applicationContext
            )
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = DetailActivity.intent(this, listAdapter.getItem(position)!!)
            startActivity(intent)
        }

        @Suppress("UNCHECKED_CAST")
        val restList = intent.getSerializableExtra(REST_LIST_EXTRA) as List<Rest>
        listAdapter.addAll(restList)
        listAdapter.notifyDataSetChanged()
    }
}
