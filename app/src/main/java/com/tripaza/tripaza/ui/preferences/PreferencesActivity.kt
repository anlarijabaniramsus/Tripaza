package com.tripaza.tripaza.ui.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tripaza.tripaza.R

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        supportActionBar?.title = "Settings"
        supportFragmentManager.beginTransaction().add(
            R.id.preference_container, PreferencesFragment()
        ).commit()
    }
    
}