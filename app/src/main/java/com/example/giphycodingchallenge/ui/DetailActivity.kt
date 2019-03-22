package com.example.giphycodingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphycodingchallenge.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent?.extras?.getString("name")
        setDetailsFragment(name.orEmpty())
    }

    private fun setDetailsFragment(param: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragmentHolder, DetailFragment.newInstance(param))
            .commit()

    }
}
