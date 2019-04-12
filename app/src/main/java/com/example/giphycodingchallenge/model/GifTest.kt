package com.example.giphycodingchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifTest(val title: String, val url: String) : Parcelable
