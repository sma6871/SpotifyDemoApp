package com.spotify.demo.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


/**
 * Shared Preferences helper class
 */
class SharedPrefHelper(private val context: Context) {

    fun defaultPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

}
