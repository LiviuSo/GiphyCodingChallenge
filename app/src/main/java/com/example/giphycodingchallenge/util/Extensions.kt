package com.example.giphycodingchallenge.util

import android.content.Context
import android.content.res.Configuration
import android.view.Surface
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.isTablet(): Boolean {
    val xlarge = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
    val large = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
    return xlarge || large
}

fun Context.isOrientedLanscape(): Boolean {
    val rotation = (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
    return rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270
}


fun AppCompatActivity.rebindFragment(tag: String): Fragment? {
    val frag = this.supportFragmentManager.findFragmentByTag(tag)
    frag?.let { it1 -> supportFragmentManager.beginTransaction().detach(it1).attach(frag).commit() }
    return frag
}