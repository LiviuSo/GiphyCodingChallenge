package com.example.giphycodingchallenge.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.amitshekhar.DebugDB
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.util.Constants.DEFAULT_QUERY
import com.example.giphycodingchallenge.util.Constants.EXTRA_ITEM
import com.example.giphycodingchallenge.util.isOrientedLanscape
import com.example.giphycodingchallenge.util.isTablet
import com.example.giphycodingchallenge.util.rebindFragment
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

/**
 * GIPHY's ​GIF​ library is the largest in the world and includes millions of original GIFs directly from
 * the world's best ​content partners​, original ​GIF artists​, as well as the best GIFs from across the entire internet.
 * GIPHY's APIs make it dead simple for developers to incorporate this vast library right inside of their apps to deliver
 * highly interactive content that is proven to increase daily engagement across all types of apps;
 * messaging, chat, dating, creation, community, and more.
 *
 *
 * Write an Image Searching Android App using the following endpoints. You can find the Gif documentation here: ​Giphy Documentation
 * - Trending: http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC
 * - Search: http://api.giphy.com/v1/gifs/search?q=funny+cat&api_key=dc6zaTOxFJmzC
 *
 * Requirements:
 * - Use the trending API to get a list of images and display them in a list or grid view
 * - Use the search endpoint to display a list of search results
 * - Clicking in each item in the list should open up a detail page with a larger image in
 *     the top half of the screen and bottom half should display user "display_name", image "rating" and the image "source".
 *     Clicking back from the details page should take you back to the list
 * - Should look decent on any size screen (phone vs tablet)
 *
 * Bonus:
 * - Get additional images with pagination as the user scrolls down through the list
 * - Use Kotlin
 * - Store items locally in a database instead of pulling from the API every time
 *
 * Note:​ You are encouraged to utilize open source libraries. The UI design is up to you!
 *
 */
class GifListActivity : AppCompatActivity() {

    private var isLandscape: Boolean = false
    private var searchOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG, DebugDB.getAddressLog()) // for DB debug

        isLandscape = isOrientedLanscape()
        Log.d(LOG, "GifListActivity: isLandscape = $isLandscape")

        if (isTablet()) {
            Log.d(LOG, "GifListActivity: tablet detected")
            setContentView(R.layout.activity_list_gif_tablet)
            setFragments(isLandscape)
        } else {
            Log.d(LOG, "GifListActivity: phone detected")
            setContentView(R.layout.activity_list_gif_phone)
            setListFragment(isLandscape)
        }

        initSearch()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(LOG, "GifListActivity ($this): onConfigurationChanged")
        isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        Log.d(LOG, "GifListActivity ($this): onConfigurationChanged: isLandscape = $isLandscape")

        if (isTablet()) {
            (rebindFragment(GIF_LIST_FRAG_TAG) as GifListFragment).onConfigChanged(isLandscape)
            rebindFragment(GIF_DETAILS_FRAG_TAG)
        } else {
            (rebindFragment(GIF_LIST_FRAG_TAG) as GifListFragment).onConfigChanged(isLandscape)
        }
    }

    private fun setFragments(landscape: Boolean) {
        Log.d(LOG, "setFragments()")
        // set both list and detail frags
        setListFragment(landscape)
        GifDetailFragment.instance(null).let {
            Log.d(LOG, "setFragments() : $it")

            supportFragmentManager.beginTransaction()
                .replace(R.id.detailsFragmentHolder, it, GIF_DETAILS_FRAG_TAG)
                .commit()
        }
    }

    private fun setListFragment(landscape: Boolean) {
        GifListFragment.instance(landscape, isTablet(), DEFAULT_QUERY).run {
            supportFragmentManager.beginTransaction()
                .add(R.id.listFragmentHolder, this, GIF_LIST_FRAG_TAG)
                .commit()
        }
    }

    fun launchDetailActivity(item: GifEntity) {
        Log.d(LOG, "GifListActivity: launchDetailActivity($item)")
        val intent = Intent(this, GifDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(EXTRA_ITEM, item)
        startActivity(intent)
    }

    fun replaceDetailFragment(item: GifEntity) {
        Log.d(LOG, "GifListActivity: replaceDetailFragment($item)")
        val frag = GifDetailFragment.instance(item)
        supportFragmentManager.beginTransaction()
            .replace(R.id.detailsFragmentHolder, frag, GIF_DETAILS_FRAG_TAG)
            .commit()
    }

    private fun showSearchBar(show: Boolean) {
        searchOn = show
        val visibilityClose = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        val visibilitySearch = if (show) {
            View.GONE
        } else {
            View.VISIBLE
        }
        searchBar.editSearchGifs.visibility = visibilityClose
        searchBar.closeSearch.visibility = visibilityClose
        searchBar.searchButton.visibility = visibilitySearch
    }

    private fun initSearch() {
        Log.d(LOG, "Init search: $searchButton")
        showSearchBar(searchOn)

        findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            Log.d(LOG, "search onClick()")
            showSearchBar(true)
        }

        editSearchGifs.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(LOG, "onTextChanged")
                (this@GifListActivity.supportFragmentManager.findFragmentByTag(GIF_LIST_FRAG_TAG) as GifListFragment).updateGifsListFromInput(
                    s.toString()
                )
            }
        })

        searchBar.closeSearch.setOnClickListener {
            searchOn = false
            editSearchGifs.setText("")
            showSearchBar(searchOn)
        }
    }

    companion object {
        const val LOG = "giphy_list_activity"
        const val GIF_LIST_FRAG_TAG = "GifListFragment"
        const val GIF_DETAILS_FRAG_TAG = "GifDetailsFragment"
    }
}