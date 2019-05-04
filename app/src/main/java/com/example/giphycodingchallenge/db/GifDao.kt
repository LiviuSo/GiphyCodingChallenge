package com.example.giphycodingchallenge.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface GifDao {

    @Query("SELECT * FROM gifs_table")
    fun getGifs(): Observable<List<GifEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gif: GifEntity)

    @Query("DELETE FROM gifs_table")
    fun deleteAll()

    @Query("SELECT * FROM gifs_table")
    fun getAllGifs(): DataSource.Factory<Int, GifEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gifs: List<GifEntity>)

    @Query("SELECT * FROM gifs_table WHERE title LIKE :query")
    fun searchGifs(query: String): DataSource.Factory<Int, GifEntity>
}