package com.example.giphycodingchallenge.paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.paging.PaginationActivity.Companion.LOG
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE

class GifBoundaryCallback(
    private val service: GifWebServicePaging,
    private val cache: GifsCache
) : PagedList.BoundaryCallback<GifEntity>() {

    private val _networkErrors = MutableLiveData<String>()

    private var lastRequestedPage = 1

    private var isRequestInProgress = false

    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: GifEntity) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        service.getTrending(lastRequestedPage,
            NETWORK_PAGE_SIZE,{ listGifs ->
                val data = arrayListOf<GifEntity>()
                listGifs.forEach {
                    data.add(GifEntity(it.title, it.images.fixedHeight.url))
                }
                cache.insert(data) {
                    Log.d(LOG, "${data.size}")
                    lastRequestedPage++
                    isRequestInProgress = false
                }
            }, {
                _networkErrors.postValue(it)
                isRequestInProgress = false
            })
    }
}
