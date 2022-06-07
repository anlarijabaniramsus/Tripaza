package com.tripaza.tripaza.helper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

object MapHelper {

    fun moveCamera(mMap: GoogleMap, latLng: LatLng){
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11F) // 0 - 18 zoom level
        mMap.animateCamera(cameraUpdate)

    }
    
}