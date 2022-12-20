package com.project.ecommerceapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.FragmentUserAddressBinding
import com.project.ecommerceapp.ui.PaymentActivity
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.ADDRESS
import com.project.ecommerceapp.utils.Constants.PIN_CODE
import com.project.ecommerceapp.utils.Constants.USER_CITY_NAME
import com.project.ecommerceapp.utils.Constants.USER_NAME
import com.project.ecommerceapp.utils.Constants.USER_PHONE_NUMBER
import com.project.ecommerceapp.utils.Constants.USER_STATE_NAME

class FragmentUserAddress : Fragment() {
    private var _binding: FragmentUserAddressBinding? = null
    private val binding get() = _binding!!
    private var sharedpreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllValues()
        binding.btnProceedToPay.setOnClickListener {
            updateAllValues()
            val intent = Intent(requireActivity(),PaymentActivity::class.java)
            intent.putExtra("totalAmount","2033")
            startActivity(intent)
        }
    }

    private fun updateAllValues() {
        if(binding.etUserName.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etUserName.requestFocus()
            binding.etUserName.error="Empty"
        }
        if(binding.etUserNumber.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etUserNumber.requestFocus()
            binding.etUserNumber.error="Empty"
        }
        if(binding.etCityName.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etCityName.requestFocus()
            binding.etCityName.error="Empty"
        }
        if(binding.etStateName.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etStateName.requestFocus()
            binding.etStateName.error="Empty"
        }
        if(binding.etPinCode.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etPinCode.requestFocus()
            binding.etPinCode.error="Empty"
        }
        if(binding.etUserAddress.toString().isNotEmpty()){
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString(USER_NAME, binding.etUserName.toString())
            editor.apply()
        }
        else{
            binding.etUserAddress.requestFocus()
            binding.etUserAddress.error="Empty"
        }
    }

    private fun getAllValues() {
        binding.etUserName.setText(sharedpreferences.getString(USER_NAME,""))
        binding.etUserNumber.setText(sharedpreferences.getString(USER_PHONE_NUMBER,""))
        binding.etCityName.setText(sharedpreferences.getString(USER_CITY_NAME,""))
        binding.etStateName.setText(sharedpreferences.getString(USER_STATE_NAME,""))
        binding.etPinCode.setText(sharedpreferences.getString(PIN_CODE,""))
        binding.etUserAddress.setText(sharedpreferences.getString(ADDRESS,""))
    }

}