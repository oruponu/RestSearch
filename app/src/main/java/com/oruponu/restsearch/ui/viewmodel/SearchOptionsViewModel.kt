package com.oruponu.restsearch.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oruponu.restsearch.BuildConfig
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.categorylarge.CategoryLargeSearch
import com.oruponu.restsearch.data.repository.CategoryLargeSearchRepository
import kotlinx.coroutines.launch

class SearchOptionsViewModel : ViewModel() {
    val dataCategory = MutableLiveData<CategoryLargeSearch>()

    val stringId = MutableLiveData<Event<Int>>()

    val selectedCategories = MutableLiveData<HashMap<String, String>>()

    private val repository = CategoryLargeSearchRepository.getCategory()

    fun getCategory() {
        viewModelScope.launch {
            try {
                val category = repository.getCategory(BuildConfig.GNAVI_API_KEY)
                dataCategory.postValue(category)
            } catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException -> {
                        stringId.postValue(Event(R.string.error_category_offline))
                    }
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            429, 500, 503 -> {
                                stringId.postValue(Event(R.string.error_category_server))
                            }
                            else -> {
                                stringId.postValue(Event(R.string.error_category_fatal))
                            }
                        }
                    }
                    else -> {
                        stringId.postValue(Event(R.string.error_category_fatal))
                    }
                }
            }
        }
    }
}
