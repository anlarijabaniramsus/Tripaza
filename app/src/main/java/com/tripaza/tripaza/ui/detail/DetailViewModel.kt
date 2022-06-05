package com.tripaza.tripaza.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import kotlin.math.abs
import kotlin.random.Random

class DetailViewModel: ViewModel() {

    private val _foodList = MutableLiveData<ArrayList<Food>>().apply {
        value = generateFoodList("VeryLongFoodString")
    }
    val foodList: LiveData<ArrayList<Food>> = _foodList

    private fun generateFoodList(identifier: String): ArrayList<Food>{
        val data = arrayListOf<Food>()
        val character = arrayListOf<String>("A", "B", "C", "D")
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
                DUMMY_IMAGE_PLACE
            ))
        }
        return data
    }
    
    
//    View Item
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    
    private val _rating = MutableLiveData<Int>()
    val rating: LiveData<Int> = _rating

    private val _lat = MutableLiveData<Double>()
    val lat: LiveData<Double> = _lat

    private val _lng = MutableLiveData<Double>()
    val lng: LiveData<Double> = _lng
    
    fun setTitle(title: String){
        _title.value = title
    }

    fun setDescription(description: String){
        _description.value = description
    }

    fun setRating(rating: Int){
        _rating.value = rating
    }

    fun setLat(lat: Double){
        _lat.value = lat
    }
    fun setLng(lng: Double){
        _lng.value = lng
    }
}