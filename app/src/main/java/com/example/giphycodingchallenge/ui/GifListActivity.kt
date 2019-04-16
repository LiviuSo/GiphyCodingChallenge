package com.example.giphycodingchallenge.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.util.isOrientedLanscape
import com.example.giphycodingchallenge.util.isTablet
import kotlinx.android.synthetic.main.toolbar.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG, "GifListActivity $this: onCreate()")

        setSupportActionBar(toolbarCustom)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        isLandscape = isOrientedLanscape()

        if (isTablet()) {
            Log.d(LOG, "GifListActivity: tablet detected")
            setFragments(isLandscape)
        } else {
            Log.d(LOG, "GifListActivity: phone detected")
            setListFragment(isLandscape)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(LOG, "GifListActivity ($this): onConfigurationChanged")
        isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        val listFrag = supportFragmentManager.findFragmentByTag(GIF_LIST_FRAG_TAG) as GifListFragment
        listFrag.onConfigChanged(isLandscape)
        listFrag.let { supportFragmentManager.beginTransaction().detach(it).attach(listFrag).commit() }
    }


    private fun setFragments(landscape: Boolean) {
        // todo
        // set both list and detail frags
        // default to first gif
    }

    private fun setListFragment(landscape: Boolean) {
        GifListFragment.instance(landscape).run {
            supportFragmentManager.beginTransaction().add(R.id.listFragment, this, GIF_LIST_FRAG_TAG).commit()
        }
    }

    companion object {
        const val LOG = "giphy_list_activity"
        const val GIF_LIST_FRAG_TAG = "GifListFragment"
    }
}

/*
* approach:
*
* - UI: RecyclerView, custom components (views)
* - architecture: same architecture as Nike App (Retail)
*       - RetrofitApi -> Retrofit observable
*       - webservice -> Observable
*       - repository -> live data
*       - modelview -> consumer live data
*
* - network: Retrofit, picasso (?)
* - Rx & LiveData
*
* - tablet, phone
* - portrait & landscape
* - test: unit, api, UI
*
* list: activity + fragment
* details: activity + fragment
*
*
* UI
*   layout
*       ConstraintLayout
*       LinearLayout
*   findById
*       androidx
*
* arch
*   MVVM
*   Repository
*   Pagination
*
* DB
*   Room
*   SQLite
*
* network
*   Retrofit
*       json
*           Moshi
*           Gson
*       image
*           Picasso
*           Glade
*   OkHttp
*
*
*
*
*
* */
