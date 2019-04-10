package com.example.giphycodingchallenge.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.giphycodingchallenge.MyApplication
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.adapter.GiphyAdapter
import com.example.giphycodingchallenge.model.Giphy
import kotlinx.android.synthetic.main.fragment_list_giphy.view.*


class GiphyListFragment: Fragment() {

    companion object {
        private const val LOG_TAG = "GiphyListFragment"
        fun instance() = GiphyListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_giphy, container, false)

        val data = arrayListOf(
            Giphy("coucou","http://i.imgur.com/DvpvklR.png"),
            Giphy("moumou","http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"),
            Giphy("zouzou","http://i.imgur.com/rT5vXE1.jpg")
        )

        view.giphys.layoutManager = GridLayoutManager(this.activity, 3)
        view.giphys.adapter = GiphyAdapter(MyApplication.myApplication, data)
        return view
    }
}
