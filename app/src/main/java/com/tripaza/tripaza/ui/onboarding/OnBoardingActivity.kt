package com.tripaza.tripaza.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tripaza.tripaza.databinding.ActivityOnBoardingBinding
import com.tripaza.tripaza.ui.navigation.MainNavigationActivity
import com.tripaza.tripaza.ui.registration.MainActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        checkIsUserAlreadyLoggedIn()
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
            finish()
        }
    }

    private fun checkIsUserAlreadyLoggedIn() {
        val devIsUserAlreadyLoggedIn = false
        if (devIsUserAlreadyLoggedIn){
            val intent = Intent(this, MainNavigationActivity::class.java)
            startActivity(intent);
            finish()
        }
    }
}