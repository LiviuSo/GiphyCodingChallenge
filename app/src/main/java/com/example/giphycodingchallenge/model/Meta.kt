package com.example.giphycodingchallenge.model

import com.google.gson.annotations.SerializedName

data class Meta(val msg: String, val status: Int, @SerializedName("response_id") val responseId: String)
