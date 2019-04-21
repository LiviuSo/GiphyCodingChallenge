package com.example.giphycodingchallenge.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "gifs_table")
@Parcelize
data class GifEntity(val title: String,
                     val url: String,
                     @PrimaryKey(autoGenerate = true) val id: Int = 0) : Parcelable

