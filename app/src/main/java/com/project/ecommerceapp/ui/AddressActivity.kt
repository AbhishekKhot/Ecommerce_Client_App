package com.project.ecommerceapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.project.ecommerceapp.databinding.ActivityAddressBinding
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.ADDRESS
import com.project.ecommerceapp.utils.Constants.PIN_CODE
import com.project.ecommerceapp.utils.Constants.SHARED_PREFERENCES_NAME
import com.project.ecommerceapp.utils.Constants.USER_CITY_NAME
import com.project.ecommerceapp.utils.Constants.USER_NAME
import com.project.ecommerceapp.utils.Constants.USER_PHONE_NUMBER
import com.project.ecommerceapp.utils.Constants.USER_STATE_NAME

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private var totalAmount: Long? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalAmount = intent.getStringExtra("totalAmount")!!.toLong()

        Toast.makeText(this, totalAmount.toString(), Toast.LENGTH_SHORT).show()

        sharedPref=getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
       editor=sharedPref.edit()

        getAllValues()
        binding.btnProceedToPay.setOnClickListener {
            updateAllValues()
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("totalAmount", totalAmount)
            startActivity(intent)
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