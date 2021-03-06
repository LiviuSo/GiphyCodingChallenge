package com.example.giphycodingchallenge.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.network.service.GifWebServicePaging
import com.example.giphycodingchallenge.ui.GifListActivity.Companion.LOG
import com.example.giphycodingchallenge.util.Constants.NETWORK_PAGE_SIZE

class GifBoundaryCallback(
    private val query: String,
    private val service: GifWebServicePaging,
    private val cache: GifsCache
) : PagedList.BoundaryCallback<GifEntity>() {

    private val _networkErrors = MutableLiveData<String>()

    private var currentOffset = 0

    private var isRequestInProgress = false

    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: GifEntity) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        Log.d(LOG, "requestAndSaveData($query)")

        if (isRequestInProgress) return

        isRequestInProgress = true
        service.getGifsFromService(
            query,
            currentOffset,
            NETWORK_PAGE_SIZE,
            { listGifEntity, offset ->
                currentOffset = offset
                cache.insert(listGifEntity) {
                    Log.d(LOG, "${listGifEntity.size}")
                    currentOffset += NETWORK_PAGE_SIZE
                    isRequestInProgress = false
                }
            }, {
                _networkErrors.postValue(it)
                isRequestInProgress = false
            }
        )
    }
}
