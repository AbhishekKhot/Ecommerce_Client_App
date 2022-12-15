package com.project.ecommerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityOtpVerificationBinding

class OtpVerificationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}