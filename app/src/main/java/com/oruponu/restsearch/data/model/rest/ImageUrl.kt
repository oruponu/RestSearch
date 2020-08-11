package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json

data class ImageUrl(
    val qrcode: String,
    @Json(name = "shop_image1")
    val shopImage1: String,
    @Json(name = "shop_image2")
    val shopImage2: String
)
