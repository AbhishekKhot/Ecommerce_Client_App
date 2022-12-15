package com.project.ecommerceapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Matcher
import java.util.regex.Pattern

class OTPReciver : BroadcastReceiver() {
    lateinit var otpReceiverListener: OtpReceiverListener
    //var otpReceiverListener: OtpReceiverListener? = null

    fun initListener(otpReceiverListener: OtpReceiverListener?) {
        this.otpReceiverListener = otpReceiverListener!!
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val bundle = intent.extras
            if (bundle != null) {
                val status: Status? = bundle[SmsRetriever.EXTRA_STATUS] as Status?
                if (status != null) {
                    when (status.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            val message = bundle[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                            if (message != null) {
                                val pattern: Pattern = Pattern.compile("\\d{6}")
                                val matcher: Matcher = pattern.matcher(message)
                                if (matcher.find()) {
                                    val myOtp: String? = matcher.group(0)
                                    otpReceiverListener.onOtpSuccess(myOtp!!)
                                }
                            }
                        }
                        CommonStatusCodes.TIMEOUT -> otpReceiverListener.onOtpTimeout()
                    }
                }
            }
        }
    }

    interface OtpReceiverListener {
        fun onOtpSuccess(otp: String)
        fun onOtpTimeout()
    }

}