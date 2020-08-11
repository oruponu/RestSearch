package com.oruponu.restsearch.data.model.categorylarge

import com.squareup.moshi.Json

data class CategoryLargeSearch(
    @Json(name = "category_l")
    val categoryL: List<CategoryL>
)
