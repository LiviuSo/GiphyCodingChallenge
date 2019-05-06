package com.example.giphycodingchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.giphycodingchallenge.data.GifsPagingRepository

class ViewModeFactory(private val repo: GifsPagingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(GifPagingViewModel::class.java)) {
            return GifPagingViewModel(repository = repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
