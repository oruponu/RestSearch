package com.oruponu.restsearch.data.repository

import com.oruponu.restsearch.BuildConfig
import com.oruponu.restsearch.data.api.RestSearchApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestSearchRepository {
    companion object {
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        private val client = OkHttpClient().newBuilder().build()
        private val retrofit = Retrofit.Builder().baseUrl(BuildConfig.GNAVI_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(client).build()

        fun search(): RestSearchApi = retrofit.create(RestSearchApi::class.java)
    }
}
