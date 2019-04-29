package com.example.giphycodingchallenge.paging

import androidx.lifecycle.ViewModel

class GifPagingViewModel(val repository: GifsPagingRepository) : ViewModel() {

    private val gifReponse = repository.getGifsResponse()

    val gifs = gifReponse.data
    val networkErrors = gifReponse.networkErrors

}
