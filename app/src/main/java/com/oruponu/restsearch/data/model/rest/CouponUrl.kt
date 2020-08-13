package com.oruponu.restsearch.data.model.rest

import java.io.Serializable

data class CouponUrl(
    val mobile: String,
    val pc: String
) : Serializable
