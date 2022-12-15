package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.ecommerceapp.databinding.FragmentProductCategoriesBinding
import com.project.ecommerceapp.databinding.FragmentProductDetailsBinding

class FragmentProductDetails : Fragment() {
    private var _binding:FragmentProductDetailsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }
}