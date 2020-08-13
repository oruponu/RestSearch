package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json
import java.io.Serializable

data class Flags(
    @Json(name = "mobile_coupon")
    val mobileCoupon: Int,
    @Json(name = "mobile_site")
    val mobileSite: Int,
    @Json(name = "pc_coupon")
    val pcCoupon: Int
) : Serializable
