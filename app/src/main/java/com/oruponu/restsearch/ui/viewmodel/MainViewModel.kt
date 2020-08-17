package com.oruponu.restsearch.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.oruponu.restsearch.BuildConfig
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.RestSearch
import com.oruponu.restsearch.data.repository.RestSearchRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val dataRestSearch = MutableLiveData<RestSearch>()

    val stringId = MutableLiveData<Event<Int>>()

    lateinit var googleMap: GoogleMap

    var latitude = .0
    var longitude = .0

    var selectedCategories = HashMap<String, String>()

    private val repository = RestSearchRepository.search()

    fun search(range: Int) {
        viewModelScope.launch {
            try {
                val rest = repository.search(
                    BuildConfig.GNAVI_API_KEY,
                    getCategoriesCode(),
                    latitude,
                    longitude,
                    range,
                    100,
                    1
                )
                dataRestSearch.postValue(rest)
            } catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException -> {
                        stringId.postValue(Event(R.string.error_search_offline))
                    }
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            404 -> {
                                stringId.postValue(Event(R.string.error_search_not_found))
                            }
                            429, 500, 503 -> {
                                stringId.postValue(Event(R.string.error_search_server))
                            }
                            else -> {
                                stringId.postValue(Event(R.string.error_search_fatal))
                            }
                        }
                    }
                    else -> {
                        stringId.postValue(Event(R.string.error_search_fatal))
                    }
                }
            }
        }
    }

    fun getCategoriesCode(): String = selectedCategories.map { it.key }.joinToString(",")
}
