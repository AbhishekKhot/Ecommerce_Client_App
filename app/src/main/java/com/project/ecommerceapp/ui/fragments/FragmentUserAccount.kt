package com.project.ecommerceapp.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.FragmentUserAccountBinding
import com.project.ecommerceapp.utils.Constants


class FragmentUserAccount : Fragment() {
    private var _binding: FragmentUserAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        sharedPref = requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPref.edit()

        getUserName()
        updateUserName()

        binding.cardOrderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentUserAccount_to_fragmentOrderHistory)
        }
        binding.tvMyCart.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentUserAccount_to_fragmentCart)
        }
        binding.cardTermsAndConditions.setOnClickListener {
            try {
                try {
                    val uri: Uri = Uri.parse("googlechrome://navigate?url=${Constants.URL}")
                    val i = Intent(Intent.ACTION_VIEW, uri)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    val uri: Uri = Uri.parse(Constants.URL)
                    val i = Intent(Intent.ACTION_VIEW, uri)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(i)
                }
            } catch (ex: Exception) {
                Log.d(ex.toString(), ex.toString())
            }
        }
    }

    private fun getUserName() {
        binding.etUserName.text = sharedPref.getString(Constants.USER_NAME, "")
    }

    private fun updateUserName() {
        if (binding.etUserName.text.toString().isNotEmpty()) {
            editor.putString(Constants.USER_NAME, binding.etUserName.text.toString())
            editor.apply()
        }
    }
}