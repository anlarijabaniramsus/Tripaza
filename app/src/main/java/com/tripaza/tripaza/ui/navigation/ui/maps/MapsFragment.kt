package com.tripaza.tripaza.ui.navigation.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.FragmentMapsBinding
import com.tripaza.tripaza.helper.Constants.MAP_BOUNDARY_BOTTOM_LATITUDE
import com.tripaza.tripaza.helper.Constants.MAP_BOUNDARY_BOTTOM_LONGITUDE
import com.tripaza.tripaza.helper.Constants.MAP_BOUNDARY_TOP_LATITUDE
import com.tripaza.tripaza.helper.Constants.MAP_BOUNDARY_TOP_LONGITUDE
import com.tripaza.tripaza.helper.Constants.MAP_FIT_TO_MARKER_PADDING
import com.tripaza.tripaza.helper.MapHelper
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.helper.HelperTools


class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var searchResultListAdapter: SearchResultListAdapter
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val markers = ArrayList<Marker>()
    private lateinit var mMap: GoogleMap
    private var deviceLocation = LatLng(0.0,0.0)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MapsViewModel
    private lateinit var selectedMarker: Marker
    
    companion object{
        private const val TAG = "MapsFragment"
    }
    
    override fun onMapReady(mMap: GoogleMap) {
        this.mMap = mMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(1.0,2.0))
        markerOptions.visible(false)
        selectedMarker = mMap.addMarker(markerOptions)!!
        
        viewModel.deviceLocation.observe(this){
            if (it != null){
                retrieveNearbyLocation()
            }
        }
        
        
        viewModel.placeList.observe(this){
            showRecyclerList()
            if (it.size > 0)
                generateMarker(it as ArrayList<Item>)
        }
        
        
        
    }

    private fun retrieveNearbyLocation() {
        Log.d(TAG, "retrieveNearbyLocation: ")
        viewModel.mapNearbySearch("restaurant", "${deviceLocation.latitude},${deviceLocation.longitude}", 1500).observe(this){
            when(it){
                is Result.Loading -> {Log.d(TAG, "retrieveNearbyLocation: LOADING")}
                is Result.Error -> {Log.d(TAG, "retrieveNearbyLocation: ERROR")}
                is Result.Success ->{
                    Log.d(TAG, "retrieveNearbyLocation: SUCCESS")
                    val newList = ArrayList<Place>()
                    if (it.data.status == "OK"){
                        for (i in it.data.results!!){
                            val place = Place(
                                id = i?.placeId.toString(),
                                name = i?.name.toString(),
                                rating = i?.rating?.toInt()?:0,
                                image = HelperTools.createMapImageLink(i?.photos?.get(0)?.photoReference.toString())
                            )
                            Log.d(TAG, "retrieveNearbyLocation: DATA ${place}")
                            newList.add(place)
                        }
                        viewModel.setPlaceList(newList)
                    }
                }
                
            }
        }
    }

    private fun generateMarker(itemList: ArrayList<Item>){
        val bounds = LatLngBounds.builder()
        for (i in itemList.indices){
            if(itemList[i].lat != 0.0 && itemList[i].lng != 0.0){
                val lat = itemList[i].lat!!
                val lng = itemList[i].lng!!

                val marker = MarkerOptions()
                val pos = LatLng(lat, lng)

                if ( !(lng < MAP_BOUNDARY_TOP_LATITUDE || lng > MAP_BOUNDARY_BOTTOM_LATITUDE ||
                            lat < MAP_BOUNDARY_BOTTOM_LONGITUDE || lat > MAP_BOUNDARY_TOP_LONGITUDE)){
                    bounds.include(pos)
                }

                marker.position(pos)
                marker.title(itemList[i].name)
                marker.snippet(itemList[i].description)
//                marker.icon(markerIcon)

                markers.add(mMap.addMarker(marker)!!)
                
            }
        }
        val boundsBuilt = bounds.build()
        val cam = CameraUpdateFactory.newLatLngBounds(boundsBuilt, MAP_FIT_TO_MARKER_PADDING)
        mMap.animateCamera(cam)

        mMap.setOnInfoWindowClickListener {
            val tag = it.tag.toString().toInt()
            tag.let {
                val data = itemList[tag]
                Toast.makeText(requireContext(), "clicked: ${data.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        this.viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        searchResultListAdapter = SearchResultListAdapter()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }


    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        
        
        this.viewModel.placeList.observe(viewLifecycleOwner){
            searchResultListAdapter.setPlaceList(it)
        }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        

    }
    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun showRecyclerList(){
        binding.rvSearchResult.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResult.adapter = searchResultListAdapter
        searchResultListAdapter.setOnItemClickCallback(object : SearchResultListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Place) {
//                Toast.makeText(requireContext(), "Item ${data.name} Clicked ${data.lat} ${data.lng}", Toast.LENGTH_SHORT).show()
                selectedMarker.position = LatLng(data.lat, data.lng)
//                val markerIcon = vectorToBitmap(R.drawable.ic_baseline_location_blue, Color.parseColor("#0000FF"))
//                selectedMarker.setIcon(markerIcon)
                MapHelper.moveCamera(mMap, LatLng(data.lat, data.lng))
                selectedMarker.isVisible = true
            }
        })
    }
    
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
                getCurrentLocation()
            }
        }
    
    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                deviceLocation = LatLng(location.latitude, location.longitude)
                viewModel.setDeviceLocation(LatLng(location.latitude, location.longitude))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location is not found. Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}