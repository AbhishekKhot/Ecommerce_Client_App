package com.project.ecommerceapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ecommerceapp.R
import com.project.ecommerceapp.adapters.CartAdapter
import com.project.ecommerceapp.databinding.FragmentCartBinding
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.ui.HomeActivity
import com.project.ecommerceapp.utils.Constants
import com.project.ecommerceapp.utils.Constants.TOTAL_AMOUNT
import com.project.ecommerceapp.viewmodel.ProductViewModel

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var viewModel: ProductViewModel
    private var totalAmount = 0
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel

        sharedPref=requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor=sharedPref.edit()

        cartAdapter = CartAdapter(viewModel)
        setUpRecyclerView()

        viewModel.getAllProducts().observe(viewLifecycleOwner) { it ->
            cartAdapter.differ.submitList(it)
            calculateTotalCost(it)
        }


        binding.btnCheckOut.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentCart_to_fragmentUserAddress)
        }

    }

    private fun calculateTotalCost(allProducts: List<ProductModel>?) {
        allProducts?.forEach {
            totalAmount += it.productSellingPrice?.toInt()!!
        }

        binding.tvTotalAmount.text=totalAmount.toString()

        editor.putString(TOTAL_AMOUNT, (totalAmount*100).toString())
        editor.apply()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = cartAdapter
        }
    }
}