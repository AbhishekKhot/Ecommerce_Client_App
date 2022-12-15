package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.ecommerceapp.databinding.FragmentUserAccountBinding

class FragmentUserAccount : Fragment() {
    private var _binding:FragmentUserAccountBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentUserAccountBinding.inflate(layoutInflater)
        return binding.root
    }
}