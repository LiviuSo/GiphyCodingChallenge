package com.example.giphycodingchallenge.paging

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.giphycodingchallenge.db.GifDb
import java.util.concurrent.Executors

object Injection {

    /**
     * Creates an instance of the [GifsCache]
     */
    fun provideCache(context: Context): GifsCache {
        val db = GifDb.getTheInstance(context)
        return GifsCache(db.gifDao, Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [GifsPagingRepository]
     */
    fun providePagingRepository(context: Context) : GifsPagingRepository {
        return GifsPagingRepository(provideCache(context), GifWebServicePaging.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context) : ViewModelProvider.Factory {
        return ViewModeFactory(providePagingRepository(context))
    }

}