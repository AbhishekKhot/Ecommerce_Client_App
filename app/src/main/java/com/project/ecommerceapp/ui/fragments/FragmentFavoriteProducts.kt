package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ecommerceapp.adapters.FavoriteProductAdapter
import com.project.ecommerceapp.databinding.FragmentFavoriteProductsBinding
import com.project.ecommerceapp.ui.HomeActivity
import com.project.ecommerceapp.viewmodel.ProductViewModel

class FragmentFavoriteProducts : Fragment() {
    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private lateinit var favoriteProductAdapter: FavoriteProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
        favoriteProductAdapter = FavoriteProductAdapter(viewModel)
        setUpRecyclerView()

        viewModel.getAllFavoriteProduct().observe(viewLifecycleOwner){
            favoriteProductAdapter.differ.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewFavoriteProduct.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = favoriteProductAdapter
        }
    }
}