package com.example.giphycodingchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.data.GifRepository

class GifViewModel : ViewModel() {

    private val repo =  GifRepository()
    private var _gifs = MutableLiveData<List<GifEntity>>()

    val gifs: LiveData<List<GifEntity>>
        get() = _gifs

    fun getGifs() = run { _gifs = repo.getGifs() }
}