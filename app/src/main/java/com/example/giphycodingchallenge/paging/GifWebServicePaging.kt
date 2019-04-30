package com.example.giphycodingchallenge.paging

import android.annotation.SuppressLint
import android.util.Log
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.network.RetrofitClient
import com.example.giphycodingchallenge.paging.PaginationActivity.Companion.LOG
import com.example.giphycodingchallenge.util.Constants.API_KEY
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE
import io.reactivex.schedulers.Schedulers

class GifWebServicePaging private constructor(){
    @SuppressLint("CheckResult")
    fun getTrending(page: Int,
                    itemsPerPage: Int,
                    onSuccess: (List<Gif>, Int) -> Unit,
                    onError: (String) -> Unit) {
        Log.d(LOG, "getTrending($page, $itemsPerPage, ...)")

        RetrofitClient.trendingApi.callTrending(apiKey = API_KEY, page = page, itemsPerPage = NETWORK_PAGE_SIZE)
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
