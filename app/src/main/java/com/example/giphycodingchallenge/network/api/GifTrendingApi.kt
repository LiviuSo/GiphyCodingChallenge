package com.example.giphycodingchallenge.network.api

import com.example.giphycodingchallenge.model.Response
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GifTrendingApi {

    /**
     * http://api.giphy.com?api_key={apiKey}
     * Trending api
     */
    @GET("/v1/gifs/trending")
    fun callTrending(@Query("api_key") apiKey: String,
                     @Query("offset") page: Int = 0,
                     @Query("limit") itemsPerPage: Int = NETWORK_PAGE_SIZE) : Observable<Response>
}