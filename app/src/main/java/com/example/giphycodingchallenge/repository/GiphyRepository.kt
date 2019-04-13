package com.example.giphycodingchallenge.repository

import androidx.lifecycle.MutableLiveData
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.network.service.GiphyWebService
import io.reactivex.schedulers.Schedulers

class GiphyRepository : SuperRepo() {

    fun getGifs(): MutableLiveData<List<Gif>> {
        val gifsLiveData = MutableLiveData<List<Gif>>()
        add(GiphyWebService.getGifs()
            .subscribeOn(Schedulers.io())
            .subscribe {
                gifsLiveData.postValue(it.data)
        })
        return gifsLiveData
    }
}