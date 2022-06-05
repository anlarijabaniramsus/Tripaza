package com.tripaza.tripaza.ui.navigation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_FEATURED
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_FOOD
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import kotlin.math.abs
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val _foodList = MutableLiveData<ArrayList<Food>>().apply {
        value = HelperTools.generateFoodList("Food")
    }
    private val _placeList = MutableLiveData<ArrayList<Place>>().apply {
        value = HelperTools.generatePlaceList("Place")
    }

    private val _featuredPlace = MutableLiveData<Item>().apply {
        value = HelperTools.generateFeaturedItem()
    }
    
    val foodList: LiveData<ArrayList<Food>> = _foodList
    val placeList: LiveData<ArrayList<Place>> = _placeList
    val featuredPlace: LiveData<Item> = _featuredPlace

}