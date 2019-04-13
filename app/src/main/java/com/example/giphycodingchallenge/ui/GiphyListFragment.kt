package com.example.giphycodingchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.giphycodingchallenge.MyApplication
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.adapter.GiphyAdapter
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.model.GifTest
import com.example.giphycodingchallenge.viewmodel.GifViewModel
import kotlinx.android.synthetic.main.fragment_list_giphy.view.*


class GiphyListFragment: Fragment() {

    private lateinit var viewModel: GifViewModel


    companion object {
        private const val LOG = "GiphyListFragment"
        fun instance() = GiphyListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GifViewModel::class.java)
        viewModel.getGifs()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_giphy, container, false)
        view.giphys.layoutManager = GridLayoutManager(this.activity, 3)

        viewModel.gifs.observe(this,
            Observer<List<Gif>> { gif ->
                val data = arrayListOf<GifTest>()
                gif.forEach {
                    data.add(GifTest(it.title, it.images.fixedHeight.url))
                }
                view.giphys.adapter = GiphyAdapter(MyApplication.myApplication, data)
            })
        return view
    }
}
