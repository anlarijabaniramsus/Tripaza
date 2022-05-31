package com.tripaza.tripaza.helper

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.tripaza.tripaza.R

class PreferencesHelper(val context: Context){
    private val pref = context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES_NAME), Context.MODE_PRIVATE)
    
    private fun getPrefIsDarkMode(): Boolean{
        return pref.getBoolean(context.getString(R.string.APP_PREFERENCES_DARK_MODE_KEY), false)
    }
    
    fun setUpDarkMode(){
        if (getPrefIsDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    
    fun isUserLoggedIn(): Boolean{
//        TODO()
        return true
    }
    
}