package com.example.giphycodingchallenge.network.service

import android.annotation.SuppressLint
import android.util.Log
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.model.ResponseEntity
import com.example.giphycodingchallenge.network.RetrofitClient
import com.example.giphycodingchallenge.ui.GifListActivity.Companion.LOG
import com.example.giphycodingchallenge.util.Constants.API_KEY
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE
import io.reactivex.schedulers.Schedulers

class GifWebServicePaging private constructor() {
    @SuppressLint("CheckResult")
    fun getGifsFromService(
        query: String,
        offset: Int,
        maxItems: Int,
        onSuccess: (List<GifEntity>, Int) -> Unit,
        onError: (String) -> Unit
    ) {
        val observable = if (query.isEmpty()) {
            Log.d(LOG, "callTrending($API_KEY, $offset, $maxItems")
            RetrofitClient.trendingApi.callTrending(apiKey = API_KEY, offset = offset, maxItems = maxItems)
        } else {
            Log.d(LOG, "callSearch($API_KEY, $query, $offset, $maxItems")
            RetrofitClient.searchApi.callSearch(API_KEY, query, offset, NETWORK_PAGE_SIZE)
        }
        observable
            .subscribeOn(Schedulers.io())
            .map { response ->
                ResponseEntity(arrayListOf<GifEntity>().apply {
                    response.data.forEach { gif ->
                        this.add(GifEntity(gif.title, gif.images.fixedHeight.url, gif.title.hashCode()))
                    }
                }, response.pagination.offset)
            }
            .subscribe({
                onSuccess(it.entities, it.offset)
            }, {
                it.message?.let { it1 -> onError(it1) }
            })
    }

    companion object {
        fun create(): GifWebServicePaging {
            return GifWebServicePaging()
        }
    }
}
