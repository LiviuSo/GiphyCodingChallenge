package com.example.giphycodingchallenge.network

import com.example.giphycodingchallenge.network.api.GiphySearchApi
import com.example.giphycodingchallenge.network.api.GiphyTrendingApi
import com.example.giphycodingchallenge.util.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val trendingApi: GiphyTrendingApi by lazy {
        createApiImpl(GiphyTrendingApi::class.java)
    }

    val searchApi: GiphySearchApi by lazy {
        createApiImpl(GiphySearchApi::class.java)
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