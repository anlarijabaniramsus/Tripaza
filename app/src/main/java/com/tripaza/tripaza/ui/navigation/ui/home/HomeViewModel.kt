package com.tripaza.tripaza.ui.navigation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_FEATURED
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_FOOD
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import kotlin.math.abs
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val _foodList = MutableLiveData<ArrayList<Food>>().apply {
        value = generateFoodList("VeryLongFoodString")
    }
    private val _placeList = MutableLiveData<ArrayList<Place>>().apply {
        value = generatePlaceList("VeryLongPlaceString")
    }

    private val _featuredPlace = MutableLiveData<Place>().apply {
        val desc = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        
        value = Place("123", "Wisata Pantai Bali", "Bali", desc, 5, 0.0, 0.0, DUMMY_IMAGE_FEATURED)
    }
    
    val foodList: LiveData<ArrayList<Food>> = _foodList
    val placeList: LiveData<ArrayList<Place>> = _placeList
    val featuredPlace: LiveData<Place> = _featuredPlace
    
    private fun generateFoodList(identifier: String): ArrayList<Food>{
        val data = arrayListOf<Food>()
        val character = arrayListOf<String>("A", "B", "C", "D")
        val div = character.size
        var ptr = 0

        for (i in 1..100){
            val rating = (abs(Random.nextInt())%5)+1
            data.add( Food("${identifier} ${character[i%div]}${ptr}", "My ${identifier} ${character[i%div]}${ptr}", "My ${identifier} Location ${character[i%div]}${ptr}", "My ${identifier} description ${character[i%div]}${ptr++}", rating, 0.0, 0.0, DUMMY_IMAGE_FOOD) )
        }
        return data
    }
    
    private fun generatePlaceList(identifier: String): ArrayList<Place>{
        val data = arrayListOf<Place>()
        val character = arrayListOf<String>("A", "B", "C", "D")
        val div = character.size
        var ptr = 0
        data.add(Place("OFFSET", "OFFSET", "OFFSET", "OFFSET", 0, 0.0, 0.0, DUMMY_IMAGE_PLACE))
        for (i in 1..100){
            val rating = (abs(Random.nextInt())%5)+1
            data.add( Place("${identifier} ${character[i%div]}${ptr}", "My ${identifier} ${character[i%div]}${ptr}", "My ${identifier} Location ${character[i%div]}${ptr}", "My ${identifier} description ${character[i%div]}${ptr++}", rating, 0.0, 0.0, DUMMY_IMAGE_PLACE) )
        }
        return data
    }

}