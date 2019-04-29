package com.example.giphycodingchallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.giphycodingchallenge.MyApplication
import java.security.AccessControlContext

@Database(entities = [GifEntity::class], version = 1)
abstract class GifDb : RoomDatabase() {

    abstract val gifDao: GifDao

    companion object {
        private var instance: GifDb? = null

        @Synchronized
        fun getTheInstance(context: Context = MyApplication.myApplication): GifDb = instance ?: let {
            val db = Room.databaseBuilder(context, GifDb::class.java, "gifs_room.db")
                .build()
            instance = db
            db
        }
    }
}