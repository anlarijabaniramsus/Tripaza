package com.tripaza.tripaza.ui.navigation.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Maps Fragment"
    }
    val text: LiveData<String> = _text
}