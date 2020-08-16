package com.oruponu.restsearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.oruponu.restsearch.R
import com.oruponu.restsearch.ui.adapter.RestaurantListAdapter
import com.oruponu.restsearch.ui.viewmodel.RestaurantListViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_list.*

class RestaurantListFragment : Fragment() {
    companion object {
        fun createInstance(
            position: Int,
            categoriesCode: String,
            latitude: Double,
            longitude: Double,
            range: Int
        ): RestaurantListFragment =
            RestaurantListFragment().also {
                it.offsetPage = position + 1
                it.categoriesCode = categoriesCode
                it.latitude = latitude
                it.longitude = longitude
                it.range = range
            }
    }

    private val viewModel: RestaurantListViewModel by viewModels()

    private var offsetPage: Int = 0
    private var categoriesCode: String = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var range = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerObserver()

        viewModel.search(categoriesCode, latitude, longitude, range, offsetPage)

        return inflater.inflate(R.layout.fragment_restaurant_list, container, false)
    }

    private fun registerObserver() {
        viewModel.dataRestSearch.observe(viewLifecycleOwner, Observer {
            val applicationContext = activity?.applicationContext ?: return@Observer
            val listAdapter = RestaurantListAdapter(applicationContext)
            listView.adapter = listAdapter
            listView.setOnItemClickListener { _, _, position, _ ->
                val intent =
                    DetailActivity.intent(applicationContext, listAdapter.getItem(position)!!)
                startActivity(intent)
            }

            listAdapter.addAll(it.rest)
            listAdapter.notifyDataSetChanged()
        })

        viewModel.stringId.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { stringId ->
                BaseActivity.showSnackbar(
                    requireActivity().findViewById(android.R.id.content),
                    stringId,
                    android.R.string.ok,
                    View.OnClickListener { return@OnClickListener })
            }
        })
    }
}
