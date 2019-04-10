package com.example.giphycodingchallenge.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.Giphy
import com.example.giphycodingchallenge.util.Constants.EXTRA_ITEM

class DetailActivity : AppCompatActivity(), DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent?.getParcelableExtra<Giphy>(EXTRA_ITEM)
        setDetailsFragment(item)
    }

    private fun setDetailsFragment(param: Giphy?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragmentHolder, DetailFragment.newInstance(param))
            .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        // smth
    }
}
