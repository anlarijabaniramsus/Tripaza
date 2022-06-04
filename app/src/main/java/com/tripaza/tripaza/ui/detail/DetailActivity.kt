package com.tripaza.tripaza.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.FoodListAdapter
import com.tripaza.tripaza.ui.preferences.PreferencesFragment

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail"
        supportFragmentManager.beginTransaction().replace(
            R.id.detail_container, DetailFragment()
        ).commit()
    }
    

}