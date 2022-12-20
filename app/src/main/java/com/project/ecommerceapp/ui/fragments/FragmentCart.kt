package com.project.ecommerceapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ecommerceapp.adapters.CartAdapter
import com.project.ecommerceapp.databinding.FragmentCartBinding
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.ui.AddressActivity
import com.project.ecommerceapp.ui.HomeActivity
import com.project.ecommerceapp.viewmodel.ProductViewModel

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var viewModel: ProductViewModel
    private var totalAmount = 0


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

        viewModel.getAllProducts().observe(viewLifecycleOwner, Observer { it ->
            cartAdapter.differ.submitList(it)
           calculateTotalCost(it)
        })

        binding.btnCheckOut.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("totalAmount", binding.tvTotalAmount.text.toString())
//            findNavController().navigate(R.id.action_fragmentCart_to_fragmentUserAddress)
 //           findNavController().navigate(R.id.action_fragmentCart_to_fragmentUserAddress)
            val intent = Intent(requireActivity(), AddressActivity::class.java)
            intent.putExtra("totalAmount",binding.tvTotalAmount.text.toString())
            startActivity(intent)

        }

    }

    private fun calculateTotalCost(allProducts: List<ProductModel>?) {
        allProducts?.forEach {
            totalAmount += it.productSellingPrice?.toInt()!!
        }

        binding.tvTotalAmount.text=totalAmount.toString()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = cartAdapter
        }
    }
}