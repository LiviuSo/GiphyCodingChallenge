package com.example.giphycodingchallenge.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.giphycodingchallenge.MyApplication.Companion.myApplication
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.adapter.GifsPagedAdapter
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.util.Constants.DEFAULT_QUERY
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_LANDSCAPE
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_TABLET
import com.example.giphycodingchallenge.util.Constants.EXTRA_SEARCH_QUERY
import com.example.giphycodingchallenge.util.Injection
import com.example.giphycodingchallenge.viewmodel.GifPagingViewModel
import kotlinx.android.synthetic.main.fragment_list_gif.*
import kotlinx.android.synthetic.main.fragment_list_gif.view.*


class GifListFragment : Fragment() {

    private var numberOfCols: Int = 0
    private var searchOn: Boolean = false
    private lateinit var viewModel: GifPagingViewModel
    private var isLandscape: Boolean = false
    private var isTablet: Boolean = false
    private lateinit var query: String
    private lateinit var adapter: GifsPagedAdapter


    private val onClickPhone: (GifEntity) -> Unit = { item ->
        (activity as GifListActivity).launchDetailActivity(item)
    }

    private val onClickTablet: (GifEntity) -> Unit = { item ->
        (activity as GifListActivity).replaceDetailFragment(item)
    }

    companion object {
        private const val LOG = "giphy_list_fragment"
        fun instance(landscape: Boolean, tablet: Boolean, query: String) = GifListFragment().apply {
            retainInstance = true
            arguments = Bundle().apply {
                putBoolean(EXTRA_IS_LANDSCAPE, landscape)
                putBoolean(EXTRA_IS_TABLET, tablet)
                putString(EXTRA_SEARCH_QUERY, query)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG, "GifListFragment ($this): onCreate")

        super.onCreate(savedInstanceState)
        isLandscape = arguments?.getBoolean(EXTRA_IS_LANDSCAPE) ?: false
        isTablet = arguments?.getBoolean(EXTRA_IS_TABLET) ?: false
        query = arguments?.getString(EXTRA_SEARCH_QUERY) ?: DEFAULT_QUERY

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(myApplication))
            .get(GifPagingViewModel::class.java)
        viewModel.getGifs(query)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG, "GifListFragment ($this): onCreateView")

        val view = inflater.inflate(R.layout.fragment_list_gif, container, false)
        numberOfCols = when {
            isTablet && !isLandscape -> { 3 }
            isTablet && isLandscape -> { 2 }
            !isTablet && !isLandscape -> { 2 }
            !isTablet && isLandscape -> { 3 }
            else -> { 0 }
        }
        view.paginationRecView.layoutManager = StaggeredGridLayoutManager(numberOfCols, StaggeredGridLayoutManager.VERTICAL)

        initAdapter(view)
        initSearch(view, query)

        view.swipeToRefreshLayout.setOnRefreshListener {
            Toast.makeText(this@GifListFragment.activity, "Refreshing", Toast.LENGTH_SHORT).show()
            viewModel.getGifs(viewModel.lastQueryValue())
            view.swipeToRefreshLayout.isRefreshing = false
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showSearchBar(searchOn)
    }

    fun onConfigChanged(landscape: Boolean) {
        isLandscape = landscape
    }

    fun showSearchBar(show: Boolean) {
        searchOn = show
        searchBar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun initAdapter(view: View) {
        Log.d(LOG, "initAdapter()")
        adapter = GifsPagedAdapter(
            numberOfCols,
            this.isTablet,
            onClickPhone = onClickPhone,
            onClickTablet = onClickTablet
        )
        view.paginationRecView.adapter = adapter
        fetchData(view)
    }

    private fun fetchData(view: View) {
        viewModel.gifs.observe(this, Observer<PagedList<GifEntity>> {
            Log.d(LOG, "observing gifs: got ${it.size} gifs")
            adapter.submitList(it)
            showEmptyList(view, it?.size == 0)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Log.d(LOG, " networkErrors: $it") // todo smth
        })
    }

    private fun initSearch(view: View, query: String) {
        if (view.searchGifs.visibility == View.VISIBLE) {
            view.searchGifs.setText(query)
        }

        view.searchGifs.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(LOG, "onTextChanged")
                updateGifsListFromInput(view)
            }
        })

        view.closeSearch.setOnClickListener {
            searchOn = false
            view.searchGifs.setText("")
            showSearchBar(searchOn)
        }
    }

    private fun updateGifsListFromInput(view: View) {
        view.searchGifs.text.trim().let {
            view.paginationRecView.scrollToPosition(0)
            showEmptyList(view, false)
            viewModel.getGifs(it.toString())
            adapter.submitList(null)
            Log.d(LOG, "last query: $it")
        }
    }

    private fun showEmptyList(view: View, show: Boolean) {
        Log.d(LOG, "showEmptyList($show)")
        if (show) {
            view.emptyList.visibility = View.VISIBLE
            view.paginationRecView.visibility = View.GONE
        } else {
            view.emptyList.visibility = View.GONE
            view.paginationRecView.visibility = View.VISIBLE
        }
    }
}
