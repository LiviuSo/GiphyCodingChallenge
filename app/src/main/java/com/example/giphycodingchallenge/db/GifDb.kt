package com.example.giphycodingchallenge.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.giphycodingchallenge.MyApplication

@Database(entities = [GifEntity::class], version = 1)
abstract class GifDb: RoomDatabase() {

    abstract val gifDao: GifDao

    companion object {
        private var instance: GifDb? = null

        @Synchronized
        fun getTheInstance(): GifDb = instance ?: let {
            val db = Room.databaseBuilder(MyApplication.myApplication,
                GifDb::class.java, "gifs_room.db")
                .build()
            instance = db
            db
        }
    }
}