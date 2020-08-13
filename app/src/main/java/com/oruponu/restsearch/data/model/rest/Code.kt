package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json
import java.io.Serializable

data class Code(
    val areacode: String,
    @Json(name = "areacode_s")
    val areacodeS: String,
    val areaname: String,
    @Json(name = "areaname_s")
    val areanameS: String,
    @Json(name = "category_code_l")
    val categoryCodeL: List<String>,
    @Json(name = "category_code_s")
    val categoryCodeS: List<String>,
    @Json(name = "category_name_l")
    val categoryNameL: List<String>,
    @Json(name = "category_name_s")
    val categoryNameS: List<String>,
    val prefcode: String,
    val prefname: String
) : Serializable
