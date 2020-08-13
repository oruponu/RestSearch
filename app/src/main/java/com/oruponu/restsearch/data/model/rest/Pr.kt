package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json
import java.io.Serializable

data class Pr(
    @Json(name = "pr_long")
    val prLong: String,
    @Json(name = "pr_short")
    val prShort: String
) : Serializable
