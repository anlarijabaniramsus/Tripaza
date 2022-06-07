package com.tripaza.tripaza.ui.navigation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.ActivityMainNavigationBinding
import com.tripaza.tripaza.helper.DoubleTapToExit
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.ui.navigation.ui.home.HomeFragment
import com.tripaza.tripaza.ui.registration.RegisterFragment

class MainNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigationBinding
    private var doubleTapToExit = DoubleTapToExit(false)
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_maps,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    
    override fun onBackPressed() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        val isHomeChecked = bottomNavigationView.menu.getItem(0).isChecked
        if (isHomeChecked){
            HelperTools.doubleTapToExit(doubleTapToExit, this)
        }else{
            super.onBackPressed()
        }
    }
}