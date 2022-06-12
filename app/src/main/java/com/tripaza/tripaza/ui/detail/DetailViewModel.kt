package com.tripaza.tripaza.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.Injection
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.helper.HelperTools

class DetailViewModel: ViewModel() {
    
    private var userRepository =  Injection.provideUserRepository()
    private val _foodList = MutableLiveData<ArrayList<Food>>()
    private val _item = MutableLiveData<Food>()
    
    val foodList: LiveData<ArrayList<Food>> = _foodList
    val item: LiveData<Food> = _item
    
    fun setItem(item: Food){
        this._item.value = item
    }
    fun setFoodList(foodList: ArrayList<Food>){
        _foodList.value = foodList
    }
    fun retrieveFoodList() = userRepository.getFoodList()
}