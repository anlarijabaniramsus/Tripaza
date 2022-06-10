package com.tripaza.tripaza.ui.navigation.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.api.Injection
import com.tripaza.tripaza.api.responses.Location
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import kotlin.math.abs
import kotlin.random.Random

class MapsViewModel : ViewModel() {
    private var mapRepository =  Injection.provideMapRepository()
    
    private val _placeList = MutableLiveData<ArrayList<Place>>()
    val placeList: LiveData<ArrayList<Place>> = _placeList
    
    fun setPlaceList(placeList: ArrayList<Place>){
        _placeList.value = placeList
    }

    private val _deviceLocation = MutableLiveData<LatLng>()
    val deviceLocation: LiveData<LatLng> = _deviceLocation

    fun setDeviceLocation(deviceLocation: LatLng){
        _deviceLocation.value = deviceLocation
    }
    
    fun mapNearbySearch(keyword: String, location: String, radius: Int) = mapRepository.mapNearbySearch(keyword, location, radius)
}