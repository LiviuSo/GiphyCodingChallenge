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
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.adapter.GifAdapter
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.model.GifTest
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_LANDSCAPE
import com.example.giphycodingchallenge.util.Constants.EXTRA_IS_TABLET
import com.example.giphycodingchallenge.viewmodel.GifViewModel
import kotlinx.android.synthetic.main.fragment_list_giphy.view.*


class GifListFragment : Fragment() {

    private lateinit var data: ArrayList<GifTest>
    private lateinit var viewModel: GifViewModel
    private var isLandscape: Boolean = false
    private var isTablet: Boolean = false

    private val onClickPhone: (GifTest) -> Unit = { item ->
        (activity as GifListActivity).launchDetailActivity(item)
    }

    private val onClickTablet: (GifTest) -> Unit = { item ->
        (activity as GifListActivity).replaceDetailFragment(item)
    }

    companion object {
        private const val LOG = "giphy_list_fragment"
        fun instance(landscape: Boolean, tablet: Boolean) = GifListFragment().apply {
            retainInstance = true
            arguments = Bundle().apply {
                putBoolean(EXTRA_IS_LANDSCAPE, landscape)
                putBoolean(EXTRA_IS_TABLET, tablet)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG, "GifListFragment ($this): onCreate")

        super.onCreate(savedInstanceState)
        isLandscape = arguments?.getBoolean(EXTRA_IS_LANDSCAPE) ?: false
        isTablet = arguments?.getBoolean(EXTRA_IS_TABLET) ?: false

        viewModel = ViewModelProviders.of(this).get(GifViewModel::class.java)
        viewModel.getGifs()
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
            Observer<List<Gif>> { gif ->
                data = arrayListOf()
                gif.forEach {
                    data.add(GifTest(it.title, it.images.fixedHeight.url))
                }
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

    fun selectFirst() {
        onClickTablet(data.first())
    }
}
