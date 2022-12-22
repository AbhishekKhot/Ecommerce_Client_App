package com.project.ecommerceapp.ui

import android.app.Activity
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityPaymentBinding
import com.project.ecommerceapp.utils.Constants.SHARED_PREFERENCES_NAME
import com.project.ecommerceapp.utils.Constants.TOTAL_AMOUNT
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
        supportActionBar?.hide()


        sharedPref = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        editor = sharedPref.edit()

        // Toast.makeText(this,sharedPref.getString(TOTAL_AMOUNT,""),Toast.LENGTH_LONG).show()

        Checkout.preload(applicationContext)
        val co = Checkout()
        val activity: Activity = this
        co.setKeyID("rzp_test_E3vkycN2g6C9Yl")
      //  UspsyFAORjn5HwqfS7yJay1B


        try {
            val options = JSONObject()
            options.put("name", R.string.app_name)
            options.put("description", "Your Order Charges")
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", sharedPref.getString(TOTAL_AMOUNT, ""))

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onPaymentSuccess(s: String?) {
        binding.ivBackground.visibility = View.VISIBLE
        binding.btnDone.visibility = View.VISIBLE
    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
}