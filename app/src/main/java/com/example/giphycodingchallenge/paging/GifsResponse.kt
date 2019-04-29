package com.example.giphycodingchallenge.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.giphycodingchallenge.db.GifEntity

class GifsResponse(
    val data: LiveData<PagedList<GifEntity>>,
    val networkErrors: LiveData<String>
)
