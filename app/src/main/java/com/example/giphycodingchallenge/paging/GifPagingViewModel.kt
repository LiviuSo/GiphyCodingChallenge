package com.example.giphycodingchallenge.paging

import androidx.lifecycle.ViewModel

class GifPagingViewModel(val repository: GifsPagingRepository) : ViewModel() {

    private val _gifReponse = repository.getGifsResponse()

    val gifs = _gifReponse.data
    val networkErrors = _gifReponse.networkErrors

}
