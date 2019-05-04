package com.example.giphycodingchallenge.network.api

import com.example.giphycodingchallenge.model.Response
import com.example.giphycodingchallenge.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GifSearchApi {

    /**
     * Search gifs
     * http://api.giphy.com/v1/gifs/search?q={searchKey}&api_key={apiKey}
     */
    @GET("/v1/gifs/search")
    fun callSearch(@Query("api_key") apiKey: String,
                   @Query("q") searchKey: String,
                   @Query("query") offset: Int,
                   @Query("limit") limit: Int = Constants.NETWORK_PAGE_SIZE
    ): Observable<Response>
}