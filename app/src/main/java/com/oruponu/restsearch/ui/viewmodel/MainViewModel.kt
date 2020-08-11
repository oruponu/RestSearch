package com.oruponu.restsearch.ui.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var latitude = .0
    var longitude = .0
    var selectedCategories: HashMap<String, String> = hashMapOf()
}
