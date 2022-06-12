package com.tripaza.tripaza.ui.navigation.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.User
import com.tripaza.tripaza.helper.PreferencesHelper

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var preferences: PreferencesHelper
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        preferences = PreferencesHelper(this)
        user = preferences.getUser()
//        viewModel.getUserProfileData(user.id).observe(this){}
        
        
        
        
    }
}