package com.example.giphycodingchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Giphy(val name: String, val imageUrl: String) : Parcelable
