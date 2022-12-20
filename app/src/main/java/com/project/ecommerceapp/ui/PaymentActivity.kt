package com.project.ecommerceapp.ui

import android.app.Activity
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityPaymentBinding
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.SHARED_PREFERENCES_NAME
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        editor = sharedPref.edit()

        Checkout.preload(applicationContext)
        val co = Checkout()
        val activity: Activity = this
        co.setKeyID("rzp_test_qXIsX4ICvf2qsb")

        //YClfrXyecD7mmhXIjmJkup2C

        try {
            val options = JSONObject()
            options.put("name", R.string.app_name)
            options.put("description", "Your Order Charges")
            options.put("image", R.drawable.splash_image)
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", intent.getStringExtra("totalAmount"))

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(s: String?) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }
}