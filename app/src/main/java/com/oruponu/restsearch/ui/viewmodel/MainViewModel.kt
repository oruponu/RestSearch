package com.oruponu.restsearch.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oruponu.restsearch.BuildConfig
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.RestSearch
import com.oruponu.restsearch.data.repository.RestSearchRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val dataRest = MutableLiveData<RestSearch>()
    val stringId = MutableLiveData<Event<Int>>()

    var latitude = .0
    var longitude = .0
    var selectedCategories: HashMap<String, String> = hashMapOf()

    private val repository = RestSearchRepository.search()

    fun search(range: Long) {
        viewModelScope.launch {
            try {
                var categoriesCode = ""
                if (selectedCategories.isNotEmpty()) {
                    categoriesCode = selectedCategories.map { it.key }.joinToString(",")
                }
                val rest = repository.search(
                    BuildConfig.GNAVI_API_KEY,
                    categoriesCode,
                    latitude,
                    longitude,
                    range,
                    100
                )
                dataRest.postValue(rest)
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
                            429 and 500 and 503 -> {
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
}
