package com.example.giphycodingchallenge.paging

import android.annotation.SuppressLint
import android.util.Log
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.network.RetrofitClient
import com.example.giphycodingchallenge.paging.PaginationActivity.Companion.LOG
import com.example.giphycodingchallenge.util.Constants.API_KEY
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE
import io.reactivex.schedulers.Schedulers

class GifWebServicePaging private constructor() {
    @SuppressLint("CheckResult")
    fun getGifsFromService(
        query: String,
        offset: Int,
        maxItems: Int,
        onSuccess: (List<Gif>, Int) -> Unit,
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
            .subscribe({
                it?.data?.let { it1 -> onSuccess(it1, it.pagination.offset) }
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
