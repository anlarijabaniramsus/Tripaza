package com.tripaza.tripaza.ui.registration

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.ActivityMainBinding
import com.tripaza.tripaza.helper.Validator
import com.tripaza.tripaza.ui.navigation.MainNavigationActivity
import com.tripaza.tripaza.ui.onboarding.OnBoardingActivity
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.api.responses.LoginResponse
import com.tripaza.tripaza.databases.dataobject.User
import com.tripaza.tripaza.helper.DoubleTapToExit
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.PreferencesHelper

class MainActivity : AppCompatActivity() {
    private lateinit var preferenceHelper: PreferencesHelper
    private lateinit var binding: ActivityMainBinding
    private var isExecutingLogin = false
    private lateinit var viewModel: MainActivityModel
    private var doubleTapToExit = DoubleTapToExit(false)
    companion object{
        const val REGISTER_EXTRA_EMAIL = "extra_email_after_create_account_success"
        const val REGISTER_EXTRA_PASSWORD = "extra_password_after_create_account_success"
        const val REGISTER_EXTRA_CODE = 1
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferenceHelper = PreferencesHelper(this)
        setContentView(binding.root)
        supportActionBar?.title = ""
        viewModel = ViewModelProvider(this).get(MainActivityModel::class.java)
        
        binding.mainTvDoNotHaveAccount.setOnClickListener{
            val  intent = Intent(this, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        }
        
        binding.mainBtnLogin.setOnClickListener {
            login()
        }
        
        // HELPER
        binding.mainEtEmail.setText("raflyramdhani12@gmail.com")
        binding.mainEtPassword.setText("rafly06")
        
    }
    
    override fun onBackPressed() {
        HelperTools.doubleTapToExit(doubleTapToExit, this)
    }
    
    private fun login(){
        if (isExecutingLogin){
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()
        }else{
            performLoginDataValidation()
        }
    }
    
    private fun performLoginDataValidation(){
        val email = binding.mainEtEmail.text.toString()
        val password = binding.mainEtPassword.text.toString()

        var allowLogin = true
        var result = Validator.isEmailValid(email)
        if (allowLogin && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.mainEtEmail.requestFocus()
            binding.mainTilEmail.error = result.errorMessage
            allowLogin = false
        }

        result = Validator.isPasswordValid(password)
        if (allowLogin && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.mainEtPassword.requestFocus()
            binding.mainTilPassword.error = result.errorMessage
            allowLogin = false
        }

        if (allowLogin){
            val email = binding.mainEtEmail.text.toString()
            val password = binding.mainEtPassword.text.toString()
            Log.d(TAG, "performLoginDataValidation: executing view model")
            viewModel.login(email, password).observe(this){
                   handleLoginResult(it)
            }
        }
    }

    private fun handleLoginResult(result: Result<LoginResponse>?) {
        when (result) {
            is Result.Loading -> {
                isExecutingLogin = true
                binding.mainProgressBar.visibility = View.VISIBLE
                Log.d(TAG, "handleLoginResult: LOADING STATE")
            }
            is Result.Error -> {
                isExecutingLogin = false
                binding.mainProgressBar.visibility = View.GONE
                binding.mainTvError.text = "Wrong username or password"
                Log.d(TAG, "handleLoginResult: ERROR STATE")
            }
            is Result.Success -> {
                Log.d(TAG, "handleLoginResult: SUCCESS STATE")
                isExecutingLogin = false
                binding.mainProgressBar.visibility = View.GONE
                if (result.data.status){
                    Log.d(TAG, "handleLoginResult: SUCCESS STATE + USER VALIDE")
                    val user = User(
                        id = result.data.user.toString()
                    )
                    preferenceHelper.setUser(user)
                    launchMainApp()
                }else{
                    Log.d(TAG, "handleLoginResult: SUCCESS STATE + USER NOT VALID")
                    binding.mainTvError.text = "Wrong username or password"
                }
            }
        }
    }

    private fun launchMainApp(){
        val intent = Intent(this, MainNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private val registerResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == REGISTER_EXTRA_CODE && result.data != null) {
            val email = result.data!!.getStringExtra(REGISTER_EXTRA_EMAIL)
            val password = result.data!!.getStringExtra(REGISTER_EXTRA_PASSWORD)
            binding.mainEtEmail.setText(email)
            binding.mainEtPassword.setText(password)
        }
    }
}