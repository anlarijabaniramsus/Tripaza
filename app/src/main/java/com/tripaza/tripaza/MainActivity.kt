package com.tripaza.tripaza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tripaza.tripaza.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.title = ""
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.mainTvDoNotHaveAccount.setOnClickListener{
            val  intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        
        
    }
}