package com.example.giphycodingchallenge

import android.app.Application
import android.util.Log

class MyApplication: Application() {

    companion object {
        private const val LOG_TAG = "MyApplication"
        lateinit var myApplication: MyApplication
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "MyApplication::onCreate()")
        myApplication = this
    }



}