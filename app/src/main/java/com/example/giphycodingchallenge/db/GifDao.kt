package com.example.giphycodingchallenge.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface GifDao {

    @Query("SELECT * from gifs_table")
    fun getGifs(): Observable<List<GifEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gif: GifEntity)

    @Query("DELETE FROM gifs_table")
    fun deleteAll()
}