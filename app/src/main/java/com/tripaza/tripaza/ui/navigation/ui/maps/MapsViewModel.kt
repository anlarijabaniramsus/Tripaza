package com.tripaza.tripaza.ui.navigation.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.databases.dataobject.Place
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
        data.add(Place("OFFSET", "OFFSET"))
        for (i in 1..10_000){
            val rating = (abs(Random.nextInt()) %5)+1
            data.add( Place("${identifier} ${character[i%div]}${ptr}", "My ${identifier} ${character[i%div]}${ptr}", "My ${identifier} Location ${character[i%div]}${ptr}", "My ${identifier} description ${character[i%div]}${ptr++}", rating) )
        }
        return data
    }
}