package com.example.giphycodingchallenge.ui

import android.content.res.Configuration
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
import com.example.giphycodingchallenge.viewmodel.GifViewModel
import kotlinx.android.synthetic.main.fragment_list_giphy.view.*


class GifListFragment : Fragment() {

    private lateinit var viewModel: GifViewModel
    private var isLandscape: Boolean = false


    companion object {
        private const val LOG = "giphy_list_fragment"
        fun instance() = GifListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG, "GifListFragment ($this): onCreate")
        viewModel = ViewModelProviders.of(this).get(GifViewModel::class.java)
        viewModel.getGifs()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG, "GifListFragment ($this): onCreateView")

        val view = inflater.inflate(R.layout.fragment_list_giphy, container, false)
        view.giphys.layoutManager = GridLayoutManager(this.activity, if(isLandscape) {3} else {2})

        viewModel.gifs.observe(this,
            Observer<List<Gif>> { gif ->
                val data = arrayListOf<GifTest>()
                gif.forEach {
                    data.add(GifTest(it.title, it.images.fixedHeight.url))
                }
                view.giphys.adapter = GifAdapter(MyApplication.myApplication, data)
            })
        return view
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(LOG, "GifListFragment ($this): onConfigurationChanged")
        isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
    }
}