package com.example.giphycodingchallenge.paging

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.example.giphycodingchallenge.data.GifRepository

class GifsPagingRepository(private val cache: GifsCache, private val service: GifWebServicePaging) {

    fun getGifs(query: String): GifsResponse {
        Log.d(GifRepository.LOG, "getTrendingGifs() ")

        val dataSourceFactory = cache.getGifsPaging(query)

        val boundaryCallback = GifBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATA_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return GifsResponse(data, networkErrors)
    }

    companion object {
        private const val DATA_PAGE_SIZE = 50
    }
}