package com.example.giphycodingchallenge.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.paging.Injection.provideViewModelFactory
import kotlinx.android.synthetic.main.activity_pagination.*

class PaginationActivity : AppCompatActivity() {

    private lateinit var viewModel: GifPagingViewModel
    private val adapter = GifsPagedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        viewModel = ViewModelProviders.of(this, provideViewModelFactory(this)).get(GifPagingViewModel::class.java)

        paginationRecView.layoutManager = GridLayoutManager(this, 2)
        initAdapter()
    }

    private fun initAdapter() {
        Log.d(LOG, "initAdapter()")
        paginationRecView.adapter = adapter
        viewModel.gifs.observe(this, Observer<PagedList<GifEntity>> {
            Log.d(LOG, "observing gifs")
            adapter.submitList(it)
            showEmptyList(it?.size == 0)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Log.d(LOG, "observing networkErrors")
            Toast.makeText(this@PaginationActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showEmptyList(show: Boolean) {
        if(show) {
            Log.d(LOG, "showEmptyList(true)")
            emptyList.visibility = View.VISIBLE
            paginationRecView.visibility = View.GONE
        } else {
            Log.d(LOG, "showEmptyList(false)")
            emptyList.visibility = View.GONE
            paginationRecView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val LOG = "PaginationActivity"
    }
}


/**
 * STEPS:
 *
 * 1. in the activity create a field viewModel (17)
 * 2. create the viewModel class (GifPagingViewModel) and a method to fetch the data (getPagedGifs())
 * 3. create a field adapter and a function initAdapter() (18, 29)
 * 4. create the class adapter (GifsPagedAdapter) of type PagedListAdapter
 * 5. implement the members of PagedListAdapter
 * 6. implement RecyclerView.ViewHolder
 * 7.
 */