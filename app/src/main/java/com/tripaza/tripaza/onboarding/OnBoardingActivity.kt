package com.tripaza.tripaza.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tripaza.tripaza.databinding.ActivityOnBoardingBinding
import com.tripaza.tripaza.ui.registration.MainActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}