package com.example.giphycodingchallenge.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "gifs_table")
@Parcelize
data class GifEntity(val title: String,
                     val url: String,
                     val width: Int,
                     val height: Int,
                     @PrimaryKey(autoGenerate = false) val id: Int = 0) : Parcelable

fun GifEntity.scaleHeightPx(newWith: Int): Int {
    val ratio = newWith.toFloat() / this.width
    return (this.height * ratio).toInt()
}