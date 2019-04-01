package com.example.giphycodingchallenge.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.util.Contants.EXTRA_NAME

class DetailActivity : AppCompatActivity(), DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent?.getStringExtra(EXTRA_NAME)
        setDetailsFragment(name.orEmpty())
    }

    private fun setDetailsFragment(param: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragmentHolder, DetailFragment.newInstance(param))
            .commit()

    }

    override fun onFragmentInteraction(uri: Uri) {
        // smth
    }
}
