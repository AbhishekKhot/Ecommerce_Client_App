package com.project.ecommerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.ecommerceapp.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private var totalAmount: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalAmount = intent.getStringExtra("totalAmount")!!.toLong()

        Toast.makeText(this, totalAmount.toString(), Toast.LENGTH_SHORT).show()
    }
}