package com.oruponu.restsearch.data.model.rest

import com.squareup.moshi.Json

data class Rest(
    val access: Access,
    val address: String,
    val budget: String,
    val category: String,
    val code: Code,
    @Json(name = "coupon_url")
    val couponUrl: CouponUrl,
    @Json(name = "credit_card")
    val creditCard: String,
    @Json(name = "e_money")
    val eMoney: String,
    val fax: String,
    val flags: Flags,
    val holiday: String,
    val id: String,
    @Json(name = "image_url")
    val imageUrl: ImageUrl,
    val latitude: String,
    val longitude: String,
    val lunch: String,
    val name: String,
    @Json(name = "name_kana")
    val nameKana: String,
    val opentime: String,
    @Json(name = "parking_lots")
    val parkingLots: String,
    val party: String,
    val pr: Pr,
    val tel: String,
    @Json(name = "tel_sub")
    val telSub: String,
    @Json(name = "update_date")
    val updateDate: String,
    val url: String,
    @Json(name = "url_mobile")
    val urlMobile: String
)
