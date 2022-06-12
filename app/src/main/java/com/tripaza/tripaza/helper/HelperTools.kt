package com.tripaza.tripaza.helper

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.MAP_API_KEY
import kotlin.math.abs
import kotlin.random.Random

object HelperTools {
    private const val TAG = "HelperTools"
    fun setUpDarkMode(context: Context){
        if (PreferencesHelper(context).isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    
    fun glideLoaderCircle(context: Context, imageUrl: String, imageView: ImageView){
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .circleCrop()
            .apply(requestOptions)
            .into(imageView)
    }
    fun glideLoaderRounded(context: Context, imageUrl: String, imageView: ImageView){
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        val roundedOptions = RequestOptions.bitmapTransform(RoundedCorners(16))
        val centerCropOptions = RequestOptions.centerCropTransform()
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .apply(centerCropOptions)
            .apply(requestOptions)
            .apply(roundedOptions)
            .into(imageView)
    }
    
    /*
    * Lat : -6.175381
    * Lng : 106.827147
    * */
    private fun latGenerator(): Double{
        val coordinate = (Random.nextDouble()*-1)+-6.175381      + -0.0003 + -0.01
//        Log.d(TAG, "coordinateGenerator: $coordinate")
        return coordinate
    }
    
    private fun lngGenerator(): Double{
        val coordinate = Random.nextDouble()+106.827147     + 0.0003
//        Log.d(TAG, "coordinateGenerator: $coordinate")
        return coordinate
    }
    
    fun generatePlaceList(identifier: String): ArrayList<Place>{
        val data = arrayListOf<Place>()
        val character = arrayListOf<String>("A", "B", "C", "D")
        val div = character.size
        var ptr = 0

        for (i in 0..20){
            val rating = (abs(Random.nextInt())%5)+1
            data.add( Place(
                "${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} Location ${character[i%div]}${ptr}",
                "My ${identifier} description ${character[i%div]}${ptr++}",
                rating,
                latGenerator(),
                lngGenerator(),
                Constants.DUMMY_IMAGE_PLACE
            ))
        }
        return data
    }

    fun generateFoodList(identifier: String): ArrayList<Food>{
        val data = arrayListOf<Food>()
        val character = arrayListOf("A", "B", "C", "D")
        val div = character.size
        var ptr = 0

        for (i in 0..10){
            val rating = (abs(Random.nextInt()) %5)+1
            data.add( Food(
                "${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} ${character[i%div]}${ptr}",
                "My ${identifier} Location ${character[i%div]}${ptr}",
                "My ${identifier} description ${character[i%div]}${ptr++}",
                rating,
                latGenerator(),
                lngGenerator(),
                Constants.DUMMY_IMAGE_FOOD
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

        return Place("x123", "Wisata Pantai Bali", "Bali", desc, 5, latGenerator(), lngGenerator(), Constants.DUMMY_IMAGE_FEATURED)
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
    fun createMapImageLink(photoReference: String): String{
        return "https://maps.googleapis.com/maps/api/place/photo?"+
            "maxwidth=480"+
            "&photo_reference=${photoReference}"+
            "&key=${MAP_API_KEY}"
    }
}