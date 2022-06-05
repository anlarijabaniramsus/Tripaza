package com.tripaza.tripaza.helper

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object HelperTools {
    fun setUpDarkMode(context: Context){
        if (PreferencesHelper(context).isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}