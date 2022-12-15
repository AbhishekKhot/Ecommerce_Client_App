package com.project.ecommerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityPhoneNumberBinding

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}