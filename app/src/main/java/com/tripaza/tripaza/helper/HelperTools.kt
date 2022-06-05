package com.tripaza.tripaza.helper

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import kotlin.math.abs
import kotlin.random.Random

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
    
    fun generateFoodList(identifier: String): ArrayList<Food>{
        val data = arrayListOf<Food>()
        val character = arrayListOf("A", "B", "C", "D")
        val div = character.size
        var ptr = 0

        for (i in 1..10){
            val rating = (abs(Random.nextInt()) %5)+1
            data.add( Food(
                "${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} Location ${character[i%div]}${ptr}",
                "My ${identifier} description ${character[i%div]}${ptr++}",
                rating,
                0.0,
                0.0,
                Constants.DUMMY_IMAGE_PLACE
            )
            )
        }
        return data
    }
    
    fun generateFeaturedItem(): Item {
        val desc = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

        return Place("x123", "Wisata Pantai Bali", "Bali", desc, 5, 0.0, 0.0, Constants.DUMMY_IMAGE_FEATURED)
    }
    
    fun generatePlaceList(identifier: String): ArrayList<Place>{
        val data = arrayListOf<Place>()
        val character = arrayListOf<String>("A", "B", "C", "D")
        val div = character.size
        var ptr = 0
        data.add(Place("OFFSET", "OFFSET", "OFFSET", "OFFSET", 0, 0.0, 0.0,
            Constants.DUMMY_IMAGE_PLACE
        ))
        for (i in 0..100){
            val rating = (abs(Random.nextInt())%5)+1
            data.add( Place(
                "${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} Location ${character[i%div]}${ptr}",
                "My ${identifier} description ${character[i%div]}${ptr++}",
                rating, 
                0.0, 
                0.0,
                Constants.DUMMY_IMAGE_PLACE
            ))
        }
        return data
    }
    
    fun doubleTapToExit(doubleTabToExit: DoubleTapToExit, activity: Activity) {
        if (doubleTabToExit.exit) {
            activity.finish()
            return
        }
        doubleTabToExit.exit = true
        Toast.makeText(activity, "Double tap to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleTabToExit.exit = false }, 2000)
    }
}