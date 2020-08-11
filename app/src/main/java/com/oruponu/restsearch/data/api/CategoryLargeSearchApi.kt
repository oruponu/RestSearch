package com.oruponu.restsearch.data.api

import com.oruponu.restsearch.data.model.categorylarge.CategoryLargeSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryLargeSearchApi {
    @GET("/master/CategoryLargeSearchAPI/v3/")
    suspend fun getCategory(@Query("keyid") keyid: String): CategoryLargeSearch
}
