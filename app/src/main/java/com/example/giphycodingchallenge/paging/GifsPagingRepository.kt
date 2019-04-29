package com.example.giphycodingchallenge.paging

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.example.giphycodingchallenge.data.GifRepository

class GifsPagingRepository(private val cache: GifsCache, private val service: GifWebServicePaging) {

    // pagination
    fun getGifsResponse() : GifsResponse {
        Log.d(GifRepository.LOG, "getGifsResponse() ")

        val dataSourceFactory = cache.getGifsPaging()

        val boundaryCallback = GifBoundaryCallback(service, cache)
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