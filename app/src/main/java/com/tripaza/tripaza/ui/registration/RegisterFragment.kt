package com.tripaza.tripaza.ui.registration

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.FragmentRegisterBinding
import com.tripaza.tripaza.helper.Validator
import com.tripaza.tripaza.helper.date.DateHelper
import java.util.*


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var isExecutingRegistration = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        str += "@mail.com"
        binding.frRegisterEtDob.setText(DateHelper.getFormattedCurrentDate("dd-MMM-yyyy"))
        binding.frRegisterEtPhone.setText("081111111111")
        binding.frRegisterEtEmail.setText(str)
        binding.frRegisterEtPassword.setText("$str PAsswoRD")
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
        var result = Validator.isInputValid(requireContext(), name)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtName.requestFocus()
            binding.frRegisterTilName.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isInputValid(requireContext(), dob)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtDob.requestFocus()
            binding.frRegisterEtDob.setText("")
            allowRegister = false
        }

        result = Validator.isEmailValid(requireContext(), email)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtEmail.requestFocus()
            binding.frRegisterTilEmail.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isPhoneValid(requireContext(), phone)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtPhone.requestFocus()
            binding.frRegisterTilPhone.error = result.errorMessage
            allowRegister = false
        }

        result = Validator.isPasswordValid(requireContext(), password)
        if (allowRegister && !result.valid){
            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.frRegisterEtPassword.requestFocus()
            binding.frRegisterTilPassword.error = result.errorMessage
            allowRegister = false
        }

        if (allowRegister){
            // DO REGISTRATION PROCESS HERE
            Toast.makeText(requireContext(), "EXECUTING REGISTRATION PROCESS", Toast.LENGTH_SHORT).show()
            if ( true /* IF Registration Success*/){
                setActivityResult()
                launchLandingFragment()
            }else{
//                toggleFormEditability()
            }
        }
    }
    
    private fun setActivityResult() {
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
