package com.example.giphycodingchallenge.data

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.example.giphycodingchallenge.network.service.GifWebServicePaging
import com.example.giphycodingchallenge.model.GifsResponse
import com.example.giphycodingchallenge.ui.GifListActivity.Companion.LOG

class GifsPagingRepository(private val cache: GifsCache, private val service: GifWebServicePaging) {

    fun getGifs(query: String): GifsResponse {
        Log.d(LOG, "getTrendingGifs() ")

        val dataSourceFactory = cache.getGifsPaging(query)

        val boundaryCallback = GifBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory,
            DATA_PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

        return GifsResponse(data, networkErrors)
    }

    companion object {
        private const val DATA_PAGE_SIZE = 50
    }
}