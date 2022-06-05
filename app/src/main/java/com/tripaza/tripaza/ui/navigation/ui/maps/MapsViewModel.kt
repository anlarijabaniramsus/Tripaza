package com.tripaza.tripaza.ui.navigation.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import kotlin.math.abs
import kotlin.random.Random

class MapsViewModel : ViewModel() {
    private val _placeList = MutableLiveData<ArrayList<Place>>().apply {
        value = generatePlaceList("VeryLongPlaceString")
    }

    val placeList: LiveData<ArrayList<Place>> = _placeList
    
    private fun generatePlaceList(identifier: String): ArrayList<Place>{
        val data = arrayListOf<Place>()
        val character = arrayListOf<String>("A", "B", "C", "D")
        val div = character.size
        var ptr = 0
        
        for (i in 1..100){
            val rating = (abs(Random.nextInt()) %5)+1
            data.add( Place("${identifier} ${character[i%div]}${ptr}", "My ${identifier} ${character[i%div]}${ptr}", "My ${identifier} Location ${character[i%div]}${ptr}", "My ${identifier} description ${character[i%div]}${ptr++}", rating, 0.0, 0.0, DUMMY_IMAGE_PLACE ))
        }
        return data
    }
}