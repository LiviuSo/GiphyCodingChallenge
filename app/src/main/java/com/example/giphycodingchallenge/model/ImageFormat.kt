package com.example.giphycodingchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageFormat(val url: String, val width: Int, val height: Int, val size: Int) : Parcelable