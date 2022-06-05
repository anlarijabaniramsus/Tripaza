package com.tripaza.tripaza.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
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
        
        
        val detailFragment: DetailFragment
        val extraBundle = intent.getBundleExtra(EXTRA_BUNDLE)
        val item = extraBundle?.getParcelable<Item>(EXTRA_DATA)
        
        val id = item?.id.toString()
        val name = item?.name.toString()
        val location = item?.location.toString()
        val description = item?.description.toString()
        val rating = item?.rating?:0
        val lat = item?.lat?:0.0
        val lng = item?.lng?:0.0
        
        item.apply {
            detailFragment = 
            DetailFragment.newInstance(
                id,
                name,
                location, 
                description, 
                rating, 
                lat,
                lng,
            )
        }
        
        supportActionBar?.title = "Detail"
        supportFragmentManager.beginTransaction().replace(
            R.id.detail_container, detailFragment
        ).commit()
    }
    

}