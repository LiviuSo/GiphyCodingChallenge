package com.example.giphycodingchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.repository.GifRepository

class GifViewModel : ViewModel() {

    private val repo =  GifRepository()
    private var _gifs = MutableLiveData<List<Gif>>()

    val gifs: LiveData<List<Gif>>
        get() = _gifs

    fun getGifs() = run { _gifs = repo.getGifs() }

}