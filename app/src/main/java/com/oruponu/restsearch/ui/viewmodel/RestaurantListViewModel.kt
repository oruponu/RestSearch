package com.oruponu.restsearch.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oruponu.restsearch.BuildConfig
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.RestSearch
import com.oruponu.restsearch.data.repository.RestSearchRepository
import kotlinx.coroutines.launch

class RestaurantListViewModel : ViewModel() {
    val dataRestSearch = MutableLiveData<RestSearch>()

    val stringId = MutableLiveData<Event<Int>>()

    private val repository = RestSearchRepository.search()

    fun search(
        categoriesCode: String,
        latitude: Double,
        longitude: Double,
        range: Int,
        offsetPage: Int
    ) {
        viewModelScope.launch {
            try {
                val rest = repository.search(
                    BuildConfig.GNAVI_API_KEY,
                    categoriesCode,
                    latitude,
                    longitude,
                    range,
                    100,
                    offsetPage
                )
                dataRestSearch.postValue(rest)
            } catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException -> {
                        stringId.postValue(Event(R.string.error_restaurant_offline))
                    }
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            429, 500, 503 -> {
                                stringId.postValue(Event(R.string.error_restaurant_server))
                            }
                            else -> {
                                stringId.postValue(Event(R.string.error_restaurant_fatal))
                            }
                        }
                    }
                    else -> {
                        stringId.postValue(Event(R.string.error_restaurant_fatal))
                    }
                }
            }
        }
    }
}
