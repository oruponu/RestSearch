package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json

data class RestSearch(
    @Json(name = "hit_per_page")
    val hitPerPage: Int,
    @Json(name = "page_offset")
    val pageOffset: Int,
    val rest: List<Rest>,
    @Json(name = "total_hit_count")
    val totalHitCount: Int
)
