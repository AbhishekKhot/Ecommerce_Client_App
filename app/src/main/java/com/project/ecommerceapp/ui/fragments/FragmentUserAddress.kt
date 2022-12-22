package com.project.ecommerceapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.FragmentUserAddressBinding
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.ADDRESS
import com.project.ecommerceapp.utils.Constants.PIN_CODE
import com.project.ecommerceapp.utils.Constants.TOTAL_AMOUNT
import com.project.ecommerceapp.utils.Constants.USER_CITY_NAME
import com.project.ecommerceapp.utils.Constants.USER_NAME
import com.project.ecommerceapp.utils.Constants.USER_PHONE_NUMBER
import com.project.ecommerceapp.utils.Constants.USER_STATE_NAME

class FragmentUserAddress : Fragment() {
    private var _binding: FragmentUserAddressBinding? = null
    private val binding get() = _binding!!
    private var totalAmount: Long? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref=requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor=sharedPref.edit()


        totalAmount=sharedPref.getString(TOTAL_AMOUNT, "")?.toLong()
        Toast.makeText(requireActivity(), totalAmount.toString(), Toast.LENGTH_SHORT).show()

        getAllValues()
        binding.btnProceedToPay.setOnClickListener {
            updateAllValues()
            findNavController().navigate(R.id.action_fragmentUserAddress_to_paymentActivity)

        }

    }

    private fun updateAllValues() {
        if (binding.etUserName.text.toString().isNotEmpty()) {
            editor.putString(USER_NAME, binding.etUserName.text.toString())
            editor.apply()
        } else {
            binding.etUserName.requestFocus()
            binding.etUserName.error = "Empty"
        }
        if (binding.etUserNumber.text.toString().isNotEmpty()) {
            editor.putString(USER_PHONE_NUMBER, binding.etUserNumber.text.toString())
            editor.apply()
        } else {
            binding.etUserNumber.requestFocus()
            binding.etUserNumber.error = "Empty"
        }
        if (binding.etCityName.text.toString().isNotEmpty()) {
            editor.putString(USER_CITY_NAME, binding.etCityName.text.toString())
            editor.apply()
        } else {
            binding.etCityName.requestFocus()
            binding.etCityName.error = "Empty"
        }
        if (binding.etStateName.text.toString().isNotEmpty()) {
            editor.putString(USER_STATE_NAME, binding.etStateName.text.toString())
            editor.apply()
        } else {
            binding.etStateName.requestFocus()
            binding.etStateName.error = "Empty"
        }
        if (binding.etPinCode.text.toString().isNotEmpty()) {
            editor.putString(PIN_CODE, binding.etPinCode.text.toString())
            editor.apply()
        } else {
            binding.etPinCode.requestFocus()
            binding.etPinCode.error = "Empty"
        }
        if (binding.etUserAddress.text.toString().isNotEmpty()) {
            editor.putString(ADDRESS, binding.etUserAddress.text.toString())
            editor.apply()
        } else {
            binding.etUserAddress.requestFocus()
            binding.etUserAddress.error = "Empty"
        }
    }

    private fun getAllValues() {
        binding.etUserName.setText(sharedPref.getString(USER_NAME, ""))
        binding.etUserNumber.setText(sharedPref.getString(USER_PHONE_NUMBER, ""))
        binding.etCityName.setText(sharedPref.getString(USER_CITY_NAME, ""))
        binding.etStateName.setText(sharedPref.getString(USER_STATE_NAME, ""))
        binding.etPinCode.setText(sharedPref.getString(PIN_CODE, ""))
        binding.etUserAddress.setText(sharedPref.getString(ADDRESS, ""))
    }


}