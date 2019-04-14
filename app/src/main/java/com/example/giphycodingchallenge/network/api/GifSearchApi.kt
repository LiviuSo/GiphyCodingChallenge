package com.example.giphycodingchallenge.network.api

import com.example.giphycodingchallenge.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GifSearchApi {

    /**
     * http://api.giphy.com/v1/gifs/search?q={searchKey}&api_key={apiKey}
     * Search gifs
     */
    @GET("/v1/gifs/search")
    fun callSearch(@Query("api_key") apiKey: String, @Query("q") searchKey: String): Observable<Response>
}