package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json
import java.io.Serializable

data class Access(
    val line: String,
    val note: String,
    val station: String,
    @Json(name = "station_exit")
    val stationExit: String,
    val walk: String
) : Serializable
