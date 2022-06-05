package com.tripaza.tripaza.helper

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object HelperTools {
    fun setUpDarkMode(context: Context){
        if (PreferencesHelper(context).isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    
    fun glideLoader(context: Context, imageUrl: String, imageView: ImageView, circleCrop: Boolean){
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        if (circleCrop)
            Glide.with(context)
                .load(imageUrl)
                .circleCrop()
                .apply(requestOptions)
                .into(imageView)
        else
            Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView)
    }
}