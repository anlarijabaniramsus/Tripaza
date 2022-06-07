package com.tripaza.tripaza.ui.navigation.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import kotlin.math.abs
import kotlin.random.Random

class MapsViewModel : ViewModel() {
    private val _placeList = MutableLiveData<ArrayList<Place>>().apply {
        value = HelperTools.generatePlaceList("VeryLongPlaceString")
    }
    val placeList: LiveData<ArrayList<Place>> = _placeList
    val getPlaceListSize = placeList.value?.size
    
//    private val _selectedMarker = MutableLiveData<MarkerOptions>().apply { value = MarkerOptions() }
//    val selectedMarker: LiveData<MarkerOptions> = _selectedMarker
////    fun setSelectedMarker(marker: MarkerOptions){
////        _selectedMarker.value = marker
////    }
}