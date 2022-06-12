package com.tripaza.tripaza.ui.camera.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tripaza.tripaza.R
import com.tripaza.tripaza.ui.navigation.ui.maps.MapsFragment

class DetectResultMapActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "DetectResultMapActivity"
        const val EXTRA_SEARCH_QUERY = "extra_search_query"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect_result_map)
        Log.d(TAG, "onCreate: ExTRA : ${intent.getStringExtra(EXTRA_SEARCH_QUERY)}")
        val mFragmentManager = supportFragmentManager
        val searchQuery = intent.getStringExtra(EXTRA_SEARCH_QUERY)?:""
        
        val mDetectResultMapFragment = MapsFragment.newInstance(searchQuery)
        val fragment = mFragmentManager.findFragmentByTag(MapsFragment::class.java.simpleName)
        if (fragment !is MapsFragment){
            mFragmentManager.beginTransaction().apply {
                replace(R.id.detect_result_map_container, mDetectResultMapFragment, MapsFragment::class.java.simpleName)
                commit()
            }
        }
        
    }
}