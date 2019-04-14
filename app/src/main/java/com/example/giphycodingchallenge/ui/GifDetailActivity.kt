package com.example.giphycodingchallenge.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.model.GifTest
import com.example.giphycodingchallenge.util.Constants.EXTRA_ITEM

class GifDetailActivity : AppCompatActivity(), DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent?.getParcelableExtra<GifTest>(EXTRA_ITEM)
        setDetailsFragment(item)
    }

    private fun setDetailsFragment(param: GifTest?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragmentHolder, DetailFragment.newInstance(param))
            .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        // smth
    }
}
