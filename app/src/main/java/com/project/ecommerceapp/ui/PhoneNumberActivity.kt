package com.project.ecommerceapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityPhoneNumberBinding
import com.project.ecommerceapp.utils.Constants.SHARED_PREFERENCES_NAME
import java.util.concurrent.TimeUnit


class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var mCallbacks: OnVerificationStateChangedCallbacks
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var countryCode: String
    private lateinit var phoneNumber:String
    private var sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryCode = binding.codePicker.selectedCountryCodeWithPlus

        binding.codePicker.setOnCountryChangeListener {
            countryCode=binding.codePicker.selectedCountryCodeWithPlus
        }

        binding.btnContinue.setOnClickListener {
            val number = binding.etPhoneNumber.text.toString()
            if (number.isEmpty()) {
                binding.etPhoneNumber.requestFocus()
                binding.etPhoneNumber.error="Empty"
            } else if(number.length<10 || number.length>10) {
                binding.etPhoneNumber.requestFocus()
                binding.etPhoneNumber.error="Not Valid Number"
            }else{
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("USER_PHONE_NUMBER", number.toString())
                editor.apply()

                binding.progressBar.visibility=View.VISIBLE
                binding.btnContinue.setBackgroundColor(R.color.grey)
                phoneNumber=countryCode+number
                val options=PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L,TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(mCallbacks)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

            override fun onVerificationFailed(p0: FirebaseException) {}

            override fun onCodeSent(s: String, foreResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, foreResendingToken)
                Toast.makeText(this@PhoneNumberActivity,"OTP sent successfully",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.INVISIBLE
                val codeSent=s
                val intent = Intent(this@PhoneNumberActivity,OtpVerificationActivity::class.java)
                intent.putExtra("otp",codeSent)
                intent.putExtra("phoneNumber",phoneNumber)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.etPhoneNumber.requestFocus()

        if(firebaseAuth.currentUser?.uid!=null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}