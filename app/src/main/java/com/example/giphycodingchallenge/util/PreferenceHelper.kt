package com.example.giphycodingchallenge.util

import android.content.Context
import android.preference.PreferenceManager
import com.example.giphycodingchallenge.MyApplication
import com.example.giphycodingchallenge.util.Constants.TIME_REFRESH_MS

class PreferenceHelper {

    private val context: Context = MyApplication.myApplication

    companion object {
        private const val SP_KEY_TIMESTAMP = "shared_pref_timestamp"
    }

    fun setTimeStamp() {
        PreferenceManager
            .getDefaultSharedPreferences(context).edit()
            .putLong(SP_KEY_TIMESTAMP, System.currentTimeMillis())
            .apply()

    }

    fun getLatestTimeStamp(): Long {
        return  PreferenceManager.getDefaultSharedPreferences(context)
            .getLong(SP_KEY_TIMESTAMP, TIME_REFRESH_MS ) // if no value in SharedPref we want to refresh
    }
}