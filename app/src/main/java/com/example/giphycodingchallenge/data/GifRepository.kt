package com.example.giphycodingchallenge.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.giphycodingchallenge.db.GifDb
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.network.service.GifWebService
import com.example.giphycodingchallenge.paging.GifsResponse
import com.example.giphycodingchallenge.util.PreferenceHelper
import com.example.giphycodingchallenge.util.isTimeToRefresh
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class GifRepository : SuperRepo() {

    private val _gifDao = GifDb.getTheInstance().gifDao

    /**
     * From web or database
     */
    fun getGifs(): MutableLiveData<List<GifEntity>> {
        val gifsLiveData = MutableLiveData<List<GifEntity>>()
        if (isTimeToRefresh()) {
            Log.d(LOG, "delete all from database")
            deleteAll()
            add(GifWebService.getGifs() // get from webservice
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.d(LOG, "set new time stamp")
                    PreferenceHelper().setTimeStamp()

                    Log.d(LOG, "save to database")
                    add(Single.fromCallable {
                        val gifEntities = arrayListOf<GifEntity>() // todo do the conversion with Rx
                        it.data.forEach {
                            val entity = GifEntity(it.title, it.images.fixedHeight.url)
                            gifEntities.add(entity)
                            _gifDao.insert(entity)
                        }
                        gifEntities
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe( Consumer {
                            gifsLiveData.postValue(it)
                        })
                    )
                })
        } else {
            // retrieve from DB
            Log.d(LOG, "retrieve from DB")
             return getAll()
        }
        return gifsLiveData
    }

    /*
     * Get the gifs from database
     */
    private fun getAll(): MutableLiveData<List<GifEntity>> {
        isTimeToRefresh()

        val gifsLiveData = MutableLiveData<List<GifEntity>>()
        add(_gifDao.getGifs().subscribe {
            gifsLiveData.postValue(it)
        })
        return gifsLiveData
    }

    /*
     * Clear the gifs from the database
     */
    private fun deleteAll() {
        add(
            Single.fromCallable {
                _gifDao.deleteAll()
            }
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {
                    Log.d(LOG, "deleted all")
                })
        )
    }

    override fun close() {
        super.close()
        Log.d("GifRepository", "closing")
    }

    companion object {
        const val LOG = "giphy_repo"
    }
}