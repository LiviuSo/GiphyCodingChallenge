package com.example.giphycodingchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.data.GifsPagingRepository
import com.example.giphycodingchallenge.util.Constants.DEFAULT_QUERY

class GifPagingViewModel(val repository: GifsPagingRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val _searchResponse = Transformations.map(queryLiveData) {
        repository.getGifs(it)
    }
    val gifs: LiveData<PagedList<GifEntity>> = Transformations.switchMap(_searchResponse) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(_searchResponse) { it.networkErrors }

    /**
     * Search a repository based on a query string.
     */
    fun getGifs(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun lastQueryValue(): String = queryLiveData.value ?: DEFAULT_QUERY

}
