package com.tripaza.tripaza.ui.navigation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.Injection
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.HelperTools

class HomeViewModel : ViewModel() {
    companion object{
        private const val TAG = "HomeViewModel"
    }
    
    private var userRepository =  Injection.provideUserRepository()
    private val _foodList = MutableLiveData<ArrayList<Food>>()
    private val _verticalList = MutableLiveData<ArrayList<Food>>()
    private val _featuredFood = MutableLiveData<Food>()
    
    val foodList: LiveData<ArrayList<Food>> = _foodList
    val placeList: LiveData<ArrayList<Food>> = _verticalList
    val featuredPlace: LiveData<Food> = _featuredFood
    
    fun retrieveFoodList() = userRepository.getFoodList()
    
    fun setPlaceList(list:  ArrayList<Food>){
        _verticalList.value = list 
    }
    
    fun setFeaturedPlace(place:  Food){
        _featuredFood.value = place
    }
    
    fun setFoodList(list: ArrayList<Food>){
        _foodList.value = list
    }
}