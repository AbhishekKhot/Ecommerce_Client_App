package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ecommerceapp.adapters.CartAdapter
import com.project.ecommerceapp.databinding.FragmentCartBinding
import com.project.ecommerceapp.ui.HomeActivity
import com.project.ecommerceapp.viewmodel.ProductViewModel

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel
        cartAdapter = CartAdapter(viewModel)
        setUpRecyclerView()

        viewModel.getAllProducts().observe(viewLifecycleOwner, Observer {
            cartAdapter.differ.submitList(it)
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = cartAdapter
        }
    }


}