package com.tripaza.tripaza

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.tripaza.tripaza.databinding.FragmentRegisterBinding
import com.tripaza.tripaza.helper.date.DateHelper
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
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
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            
            DatePickerDialog(requireContext(),{
                    view: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val text = DateHelper.formatDate(selectedDay, selectedMonth, selectedYear)
                binding.frRegisterEtDob.setText(text)
            }, year, month, day).show()
        }
        
        binding.frRegisterAlreadyHaveAccount.setOnClickListener { 
            activity?.onBackPressed()
        }
    }
}