package com.tripaza.tripaza.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.databinding.ActivityMainBinding
import com.tripaza.tripaza.helper.Validator
import com.tripaza.tripaza.ui.navigation.MainNavigationActivity
import com.tripaza.tripaza.ui.onboarding.OnBoardingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isExecutingRegistration = false
    private lateinit var viewModel: MainActivityModel
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
        viewModel = ViewModelProvider(this).get(MainActivityModel::class.java)
        viewModel.SET_DEVELOPMENT_ONLY_CONTEXT = this
        binding.mainTvDoNotHaveAccount.setOnClickListener{
            val  intent = Intent(this, RegisterActivity::class.java)
            registerResultLauncher.launch(intent)
        }
        binding.mainBtnLogin.setOnClickListener {
            login()
        }
        viewModel.isLoading.observe(this){
            if (it){
                binding.mainProgressBar.visibility = View.VISIBLE
            }else{
                binding.mainProgressBar.visibility = View.GONE
                isError(true)
            }
        }
        viewModel.isError.observe(this){
            isError(it)
        }
        // HELPER
        binding.mainEtEmail.setText("raflyramdhani12@gmail.com")
        binding.mainEtPassword.setText("rafly06")

        
    }




    private fun login(){
        if (isExecutingRegistration){
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()
        }else{
            performLoginDataValidation()
        }
    }
    
    private fun performLoginDataValidation(){
        val email = binding.mainEtEmail.text.toString()
        val password = binding.mainEtPassword.text.toString()

        var allowLogin = true
        var result = Validator.isEmailValid(this, email)
        if (allowLogin && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.mainEtEmail.requestFocus()
            binding.mainTilEmail.error = result.errorMessage
            allowLogin = false
        }

        result = Validator.isPasswordValid(this, password)
        if (allowLogin && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.mainEtPassword.requestFocus()
            binding.mainTilPassword.error = result.errorMessage
            allowLogin = false
        }

        if (allowLogin){
            val email = binding.mainEtEmail.text.toString()
            val password = binding.mainEtPassword.text.toString()
            viewModel.login(email, password)
            viewModel.loginResponse.observe(this){
                if (it.status == true)
                    launchMainApp()
            }
        }
    }
    private fun isError(isError: Boolean){
        if (isError){
            binding.mainTvError.text = "Wrong email or password"
            binding.mainTvError.visibility = View.VISIBLE
        }else{
            binding.mainTvError.visibility = View.GONE
        }
    }
    
    private val registerResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == REGISTER_EXTRA_CODE && result.data != null) {
            val email = result.data!!.getStringExtra(REGISTER_EXTRA_EMAIL)
            val password = result.data!!.getStringExtra(REGISTER_EXTRA_PASSWORD)
            binding.mainEtEmail.setText(email)
            binding.mainEtPassword.setText(password)
        }
    }
    
    private fun devImmediateLaunchOnboarding() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
    }

    private fun launchMainApp(){
        val intent = Intent(this, MainNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
}