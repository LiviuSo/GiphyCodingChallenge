package com.example.giphycodingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphycodingchallenge.R

/**
 * GIPHY's ​GIF​ library is the largest in the world and includes millions of original GIFs directly from
 * the world's best ​content partners​, original ​GIF artists​, as well as the best GIFs from across the entire internet.
 * GIPHY's APIs make it dead simple for developers to incorporate this vast library right inside of their apps to deliver
 * highly interactive content that is proven to increase daily engagement across all types of apps;
 * messaging, chat, dating, creation, community, and more.
 *
 *
 * Write an Image Searching Android App using the following endpoints. You can find the Giphy documentation here: ​Giphy Documentation
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
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListFragment()
    }

    private fun setListFragment() {
        supportFragmentManager.beginTransaction().add(R.id.listFragment, GiphyListFragment.instance()).commit()
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
* */
