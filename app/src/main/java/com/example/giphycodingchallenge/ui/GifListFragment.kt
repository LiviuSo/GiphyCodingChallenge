package com.example.giphycodingchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.giphycodingchallenge.MyApplication
import com.example.giphycodingchallenge.MyApplication.Companion.myApplication
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.adapter.GifAdapter
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.util.Constants.DEFAULT_QUERY
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_LANDSCAPE
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_TABLET
import com.example.giphycodingchallenge.util.Constants.EXTRA_SEARCH_QUERY
import com.example.giphycodingchallenge.util.Injection
import com.example.giphycodingchallenge.util.PreferenceHelper
import com.example.giphycodingchallenge.util.isTimeToRefresh
import com.example.giphycodingchallenge.viewmodel.GifPagingViewModel
import com.example.giphycodingchallenge.viewmodel.GifViewModel
import kotlinx.android.synthetic.main.fragment_list_giphy.view.*


class GifListFragment : Fragment() {

    private lateinit var data: List<GifEntity>
    private lateinit var viewModel: GifPagingViewModel
    private var isLandscape: Boolean = false
    private var isTablet: Boolean = false
    private lateinit var query: String

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

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(myApplication)).get(GifPagingViewModel::class.java)
        viewModel.getGifs(query)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG, "GifListFragment ($this): onCreateView")

        val view = inflater.inflate(R.layout.fragment_list_giphy, container, false)
        view.giphys.layoutManager = GridLayoutManager(
            this.activity, if (isLandscape) {
                3
            } else {
                2
            }
        )

        viewModel.gifs.observe(this,
            Observer<List<GifEntity>> { items ->
                data = items
                view.giphys.adapter = GifAdapter(MyApplication.myApplication, data, isTablet,
                    onClickPhone, onClickTablet)
                if(isTablet) {
                    selectFirst()
                }
            })
        return view
    }

    fun onConfigChanged(landscape: Boolean) {
        isLandscape = landscape
    }

    private fun selectFirst() {
        onClickTablet(data.first())
    }
}
