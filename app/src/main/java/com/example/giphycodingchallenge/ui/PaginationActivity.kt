package com.example.giphycodingchallenge.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.amitshekhar.DebugDB
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.viewmodel.GifPagingViewModel
import com.example.giphycodingchallenge.adapter.GifsPagedAdapter
import com.example.giphycodingchallenge.util.Injection.provideViewModelFactory
import com.example.giphycodingchallenge.util.Constants
import com.example.giphycodingchallenge.util.Constants.DEFAULT_QUERY
import com.example.giphycodingchallenge.util.Constants.LAST_SEARCH_QUERY
import com.example.giphycodingchallenge.util.isTablet
import kotlinx.android.synthetic.main.activity_pagination.*

class PaginationActivity : AppCompatActivity() {

    private lateinit var viewModel: GifPagingViewModel
    private lateinit var adapter: GifsPagedAdapter
    private val onClickPhone: (GifEntity) -> Unit = {
        launchDetailActivity(it)
    }
    private val onClickTablet: (GifEntity) -> Unit = {
        launchDetailActivity(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        Log.d(LOG, DebugDB.getAddressLog())

        viewModel = ViewModelProviders.of(this, provideViewModelFactory(this)).get(GifPagingViewModel::class.java)

        paginationRecView.layoutManager = GridLayoutManager(this, 2)
        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.getGifs(query)

        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initSearch(query: String) {
        if(searchGifs.visibility == View.VISIBLE) {
            searchGifs.setText(query)
        }

        searchGifs.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateGifsListFromInput()
            }
        })
    }

    private fun updateGifsListFromInput() {
        searchGifs.text.trim().let {
            if(it.isNotEmpty()) {
                paginationRecView.scrollToPosition(0)
                viewModel.getGifs(it.toString())
                adapter.submitList(null) // todo fix
                Log.d(LOG, "last query: $it")
            }
        }
    }

    private fun initAdapter() {
        Log.d(LOG, "initAdapter()")
        adapter = GifsPagedAdapter(
            isTablet(),
            onClickPhone = onClickPhone,
            onClickTablet = onClickTablet
        )
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

    fun launchDetailActivity(item: GifEntity) {
        Log.d(GifListActivity.LOG, "GifListActivity: launchDetailActivity($item)")
        val intent = Intent(this, GifDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Constants.EXTRA_ITEM, item)
        startActivity(intent)
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