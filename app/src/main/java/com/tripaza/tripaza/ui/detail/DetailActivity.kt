package com.tripaza.tripaza.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Place

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_BUNDLE = "extra_bundle"
        private const val TAG = "DetailActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        
        
        val extraBundle = intent.getBundleExtra(EXTRA_BUNDLE)
        val place = extraBundle?.getParcelable<Place>(EXTRA_DATA)
        val detailFragment: DetailFragment
        place.apply {
            detailFragment = DetailFragment.newInstance(
                place?.name.toString(), 
                place?.description.toString(), 
                place?.rating?:0, 
            0.0,
            0.0    
            )
        }
        
        
        supportActionBar?.title = "Detail"
        supportFragmentManager.beginTransaction().replace(
            R.id.detail_container, detailFragment
        ).commit()
    }
    

}