package com.example.giphycodingchallenge.model

import com.example.giphycodingchallenge.db.GifEntity

data class ResponseEntity(val entities: List<GifEntity>, val offset: Int)