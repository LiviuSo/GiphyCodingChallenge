package com.example.giphycodingchallenge.network.api

import com.example.giphycodingchallenge.model.Response
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GifTrendingApi {

    /**
     * Trending api
     *  http://api.giphy.com/v1/gifs/trending?api_key={api}
     */
    @GET("/v1/gifs/trending")
    fun callTrending(
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") maxItems: Int = NETWORK_PAGE_SIZE
    ): Observable<Response>
}