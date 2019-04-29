package com.example.giphycodingchallenge.paging

import androidx.paging.DataSource
import com.example.giphycodingchallenge.db.GifDao
import com.example.giphycodingchallenge.db.GifEntity
import java.util.concurrent.Executor

class GifsCache(private val gifsDao: GifDao, private val ioExecutor: Executor) {

    fun getGifsPaging(): DataSource.Factory<Int, GifEntity> {
        return gifsDao.getGifsPaging()
    }

    fun insert(gifs: List<GifEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            gifsDao.insert(gifs = gifs)
            insertFinished()
        }
    }
}