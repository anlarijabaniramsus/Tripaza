package com.tripaza.tripaza.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.databases.dataobject.Food
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
            data.add( Food("${identifier} ${character[i%div]}${ptr}", "My ${identifier} ${character[i%div]}${ptr}", "My ${identifier} Location ${character[i%div]}${ptr}", "My ${identifier} description ${character[i%div]}${ptr++}", rating) )
        }
        return data
    }
}