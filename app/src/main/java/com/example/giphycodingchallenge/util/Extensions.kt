package com.example.giphycodingchallenge.util

import android.content.Context
import android.content.res.Configuration
import android.view.Surface
import android.view.WindowManager

fun Context.isTablet(): Boolean {
    val xlarge = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
    val large = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
    return xlarge || large
}

fun Context.isOrientedLanscape(): Boolean {
    val rotation = (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
    return rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270
}
