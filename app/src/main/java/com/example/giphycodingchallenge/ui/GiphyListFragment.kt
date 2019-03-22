package com.example.giphycodingchallenge.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.giphycodingchallenge.R


class GiphyListFragment: Fragment() {

    companion object {
        private const val LOG_TAG = "GiphyListFragment"
        fun instance() = GiphyListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_giphy, container, false)
        view.findViewById<Button>(R.id.giphy1).setOnClickListener {
            Log.d(LOG_TAG, "giphy1")
            startActivity(Intent(this@GiphyListFragment.activity, DetailActivity::class.java).putExtra("name", "giphy1"))
        }
        view.findViewById<Button>(R.id.giphy2).setOnClickListener {
            Log.d(LOG_TAG, "giphy2")
            startActivity(Intent(this@GiphyListFragment.activity, DetailActivity::class.java).putExtra("name", "giphy2"))
        }
        return view
    }

}