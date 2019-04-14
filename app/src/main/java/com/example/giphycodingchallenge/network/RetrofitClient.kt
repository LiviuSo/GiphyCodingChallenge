package com.example.giphycodingchallenge.network

import com.example.giphycodingchallenge.network.api.GifSearchApi
import com.example.giphycodingchallenge.network.api.GifTrendingApi
import com.example.giphycodingchallenge.util.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val trendingApi: GifTrendingApi by lazy {
        createApiImpl(GifTrendingApi::class.java)
    }

    val searchApi: GifSearchApi by lazy {
        createApiImpl(GifSearchApi::class.java)
    }

    private fun <T> createApiImpl(api: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(api)
    }
}