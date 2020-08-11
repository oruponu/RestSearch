package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json

data class Pr(
    @Json(name = "pr_long")
    val prLong: String,
    @Json(name = "pr_short")
    val prShort: String
)
