package com.example.giphycodingchallenge.network.service

import com.example.giphycodingchallenge.model.Response
import com.example.giphycodingchallenge.network.RetrofitClient
import com.example.giphycodingchallenge.util.Constants.API_KEY
import io.reactivex.Observable

object GifWebService {

    fun getGifs(): Observable<Response> {
        return RetrofitClient
            .trendingApi
            .callTrending(API_KEY)
    }

    fun searchGifs(searchKey: String): Observable<Response> {
        return RetrofitClient
            .searchApi
            .callSearch(API_KEY, searchKey, 1)
    }
}