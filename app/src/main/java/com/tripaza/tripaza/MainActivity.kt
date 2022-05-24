package com.tripaza.tripaza

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tripaza.tripaza.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val REGISTER_EXTRA_EMAIL = "extra_email_after_create_account_success"
        const val REGISTER_EXTRA_PASSWORD = "extra_password_after_create_account_success"
        const val REGISTER_EXTRA_CODE = 1
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""
        
        binding.mainTvDoNotHaveAccount.setOnClickListener{
            val  intent = Intent(this, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        }
    }
    
    private val registerResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == REGISTER_EXTRA_CODE && result.data != null) {
            val email = result.data!!.getStringExtra(REGISTER_EXTRA_EMAIL)
            val password = result.data!!.getStringExtra(REGISTER_EXTRA_PASSWORD)
            binding.mainEtUsername.setText(email)
            binding.mainEtPassword.setText(password)
        }
    }
}