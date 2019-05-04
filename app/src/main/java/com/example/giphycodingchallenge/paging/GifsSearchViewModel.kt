package com.example.giphycodingchallenge.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GifsSearchViewModel : ViewModel() {

    private val _queryLiveData = MutableLiveData<String>()

    /**
     * Search a repository based on a query string.
     */
    fun searchGifs(query: String) {
        _queryLiveData.postValue(query)
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = _queryLiveData.value
}