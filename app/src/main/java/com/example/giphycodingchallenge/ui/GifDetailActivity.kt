package com.example.giphycodingchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.util.Constants.EXTRA_ITEM

class GifDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent?.getParcelableExtra<GifEntity>(EXTRA_ITEM)
        setDetailsFragment(item)
    }

    private fun setDetailsFragment(param: GifEntity?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragmentHolder, GifDetailFragment.instance(param))
            .commit()
    }
}
