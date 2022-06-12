package com.tripaza.tripaza.helper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

object MapHelper {

    fun moveCamera(mMap: GoogleMap, latLng: LatLng, zoom: Float = 11.0F){
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom) // 0 - 18 zoom level
        mMap.animateCamera(cameraUpdate)

    }
    
}