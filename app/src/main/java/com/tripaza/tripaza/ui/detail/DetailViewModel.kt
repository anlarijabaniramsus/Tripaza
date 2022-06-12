package com.tripaza.tripaza.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.helper.HelperTools

class DetailViewModel: ViewModel() {

    private val _foodList = MutableLiveData<ArrayList<Food>>().apply {
//        value = HelperTools.generateFoodList("It's MyFood")
    }
    val foodList: LiveData<ArrayList<Food>> = _foodList
    
    private val _item = MutableLiveData<Food>()
    val item: LiveData<Food> = _item
    
    fun setItem(item: Food){
        this._item.value = item
    }
}