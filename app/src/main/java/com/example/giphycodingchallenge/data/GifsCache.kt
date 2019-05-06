package com.example.giphycodingchallenge.data

import androidx.paging.DataSource
import com.example.giphycodingchallenge.db.GifDao
import com.example.giphycodingchallenge.db.GifEntity
import java.util.concurrent.Executor

class GifsCache(private val gifsDao: GifDao, private val ioExecutor: Executor) {

    fun getGifsPaging(query: String): DataSource.Factory<Int, GifEntity> = if(query.isEmpty()) {
        gifsDao.getAllGifs()
    } else {
        gifsDao.searchGifs("%$query%")
    }

    fun insert(gifs: List<GifEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            gifsDao.insert(gifs = gifs)
            insertFinished()
        }
    }
}