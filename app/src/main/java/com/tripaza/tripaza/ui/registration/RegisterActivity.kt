package com.tripaza.tripaza.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tripaza.tripaza.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.title =""
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // start register fragment
        val mFragmentManager = supportFragmentManager
        val mRegisterFragment = RegisterFragment()
        val fragment = mFragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)
        if (fragment !is RegisterFragment){
            mFragmentManager.beginTransaction().apply { 
                replace(R.id.register_fragment_container, mRegisterFragment, RegisterFragment::class.java.simpleName)
                commit()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}