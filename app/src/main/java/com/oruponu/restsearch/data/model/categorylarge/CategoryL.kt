package com.oruponu.restsearch.data.model.categorylarge

import com.squareup.moshi.Json

data class CategoryL(
    @Json(name = "category_l_code")
    val categoryLCode: String,
    @Json(name = "category_l_name")
    val categoryLName: String
)
