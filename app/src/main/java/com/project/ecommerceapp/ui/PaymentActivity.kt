package com.project.ecommerceapp.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.ecommerceapp.databinding.ActivityPaymentBinding
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.SHARED_PREFERENCES_NAME
import com.project.ecommerceapp.utils.Constants.USER_NAME

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPaymentBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref=getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

        val intent = Intent()
        binding.text1.text=intent.getStringExtra("totalAmount")

      Toast.makeText(this,sharedPref.getString(USER_NAME, "").toString(),Toast.LENGTH_LONG).show()
    }
}