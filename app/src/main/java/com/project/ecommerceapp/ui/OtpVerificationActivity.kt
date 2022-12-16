package com.project.ecommerceapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ActivityOtpVerificationBinding
import java.util.concurrent.TimeUnit

class OtpVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpVerificationBinding
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResendOTP.setOnClickListener {
            resendOTP()
        }

        binding.btnVerify.setOnClickListener {
            val OTP = binding.etOTP.text.toString()
            if (OTP.isEmpty()) {
                binding.etOTP.requestFocus()
                binding.etOTP.error = "Empty"
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val codeReceived = intent.getStringExtra("otp")
                val credential = PhoneAuthProvider.getCredential(codeReceived!!, OTP)
                signInWithPhoneAuthCredential(credential)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                binding.progressBar.visibility = View.INVISIBLE
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Verification failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendOTP() {
        binding.progressBar.visibility = View.VISIBLE

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@OtpVerificationActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(intent.getStringExtra("phoneNumber")!!.trim { it <= ' ' })
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onStart() {
        super.onStart()
        binding.etOTP.requestFocus()
    }
}