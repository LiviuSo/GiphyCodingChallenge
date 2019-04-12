package com.example.giphycodingchallenge.network.api

import com.example.giphycodingchallenge.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyTrendingApi {

    /**
     * http://api.giphy.com?api_key={apiKey}
     * Trending api
     */
    @GET("/v1/gifs/trending")
    fun callTrending(@Query("api_key") apiKey: String) : Observable<Response>
}