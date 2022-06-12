package com.tripaza.tripaza.ui.registration

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.FragmentRegisterBinding
import com.tripaza.tripaza.helper.Validator
import com.tripaza.tripaza.helper.DateHelper
import java.util.*
import com.tripaza.tripaza.api.Result


class RegisterFragment : Fragment() {
    
    
    companion object{
        private const val TAG = "RegisterFragment"
    }
    
    
    private lateinit var binding: FragmentRegisterBinding
    private var isExecutingRegistration = false
    private lateinit var viewModel: RegisterActivityModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterActivityModel::class.java)
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.frRegisterEtDob.showSoftInputOnFocus = false
        binding.frRegisterEtDob.setOnClickListener {
            setUpDatePicker()
        }
        binding.frRegisterAlreadyHaveAccount.setOnClickListener { 
            activity?.onBackPressed()
        }
        binding.frRegisterBtnRegister.setOnClickListener{
            register()
        }
        devAutoFill()
    }
    private fun devAutoFill(){
        val dateStr = DateHelper.getFormattedCurrentDate("yyyyMMddHHmmss")
        var str = "User$dateStr"
        binding.frRegisterEtName.setText(str)
        str += "@gmail.com"
        binding.frRegisterEtDob.setText(DateHelper.getFormattedCurrentDate("yyyy-MM-dd"))
        binding.frRegisterEtPhone.setText("081111111111")
        binding.frRegisterEtEmail.setText(str)
        binding.frRegisterEtPassword.setText("$str Password")
    }
    private fun register() {
        if (isExecutingRegistration){
            Toast.makeText(requireContext(), "Please wait", Toast.LENGTH_SHORT).show()
        }else{
            performRegistrationValidation()
        }
    }

    private fun performRegistrationValidation() {
        val name = binding.frRegisterEtName.text.toString()
        val dob = binding.frRegisterEtDob.text.toString()
        val email = binding.frRegisterEtEmail.text.toString()
        val phone = binding.frRegisterEtPhone.text.toString()
        val password = binding.frRegisterEtPassword.text.toString()

        var allowRegister = true
        var result = Validator.isInputValid(name)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtName.requestFocus()
            binding.frRegisterTilName.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isInputValid(dob)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtDob.requestFocus()
            binding.frRegisterEtDob.setText("")
            allowRegister = false
        }

        result = Validator.isEmailValid(email)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtEmail.requestFocus()
            binding.frRegisterTilEmail.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isPhoneValid(phone)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtPhone.requestFocus()
            binding.frRegisterTilPhone.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isPasswordValid(password)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtPassword.requestFocus()
            binding.frRegisterTilPassword.error = result.errorMessage
            allowRegister = false
        }

        if (allowRegister){
            viewModel.register(email, password, name, dob, phone).observe(viewLifecycleOwner){
                Toast.makeText(requireContext(), "EXECUTING REGISTRATION PROCESS", Toast.LENGTH_SHORT).show()
                isExecutingRegistration = true
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        isExecutingRegistration = false
                        binding.progressBar.visibility = View.GONE
                        binding.frRegisterTvError.text = "Registration Failed"
                    }
                    is Result.Success -> {
                        isExecutingRegistration = false
                        binding.frRegisterTvError.text = ""
                        binding.progressBar.visibility = View.GONE
                        if (it.data.status == "success"){
                            onCreateUserSuccess()
                            launchLandingFragment()
                        }else{
                            binding.frRegisterTvError.text = "Registration Failed"
                        }
                    }
                }
            }
        }
    }
    
    private fun onCreateUserSuccess() {
        val email = binding.frRegisterEtEmail.text.toString()
        val password = binding.frRegisterEtPassword.text.toString()
        
        val i = Intent()
        i.putExtra(MainActivity.REGISTER_EXTRA_EMAIL, email)
        i.putExtra(MainActivity.REGISTER_EXTRA_PASSWORD, password)
        activity?.setResult(MainActivity.REGISTER_EXTRA_CODE, i)
    }
    
    private fun launchLandingFragment(){
        (activity as AppCompatActivity).supportActionBar?.hide()
        val mFragmentManager = parentFragmentManager
        val mRegisterFragmentLanding = RegisterFragmentLanding()
        val fragment = mFragmentManager.findFragmentByTag(RegisterFragmentLanding::class.java.simpleName)
        if (fragment !is RegisterFragment){
            mFragmentManager.beginTransaction().apply {
                replace(R.id.register_fragment_container, mRegisterFragmentLanding, RegisterFragmentLanding::class.java.simpleName)
                commit()
            }
        }
    }
    private fun setUpDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(),{
                view: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val text = DateHelper.formatDate(selectedDay, selectedMonth, selectedYear)
            binding.frRegisterEtDob.setText(text)
        }, year, month, day)
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { _, act ->
            if (act == DialogInterface.BUTTON_NEGATIVE) {
                binding.frRegisterEtDob.setText("")
            }
        }
        datePicker.show()
    }
}
