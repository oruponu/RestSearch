package com.oruponu.restsearch.data.api

import com.oruponu.restsearch.data.model.rest.RestSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface RestSearchApi {
    @GET("/RestSearchAPI/v3/")
    suspend fun search(
        @Query("keyid") keyid: String,
        @Query("category_l", encoded = true) categoryL: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("range") range: Long,
        @Query("hit_per_page") hitPerPage: Int
    ): RestSearch
}
