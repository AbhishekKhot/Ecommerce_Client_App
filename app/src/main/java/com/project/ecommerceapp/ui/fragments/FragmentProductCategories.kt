package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.ecommerceapp.adapters.SpecificCategoryProducts
import com.project.ecommerceapp.databinding.FragmentProductCategoriesBinding
import com.project.ecommerceapp.model.Product
import com.project.ecommerceapp.utils.GridSpacingItemDecoration

class FragmentProductCategories : Fragment() {
    private var _binding: FragmentProductCategoriesBinding? = null
    private val binding get() = _binding!!
    private val firebase = Firebase.firestore
    private val args: FragmentProductCategoriesArgs by navArgs()
    private val categoriesProductAdapter = SpecificCategoryProducts()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrangeItemsRecyclerView()

        val list = ArrayList<Product>()
        firebase.collection("Products").whereEqualTo("product_category", args.categoryName)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Product::class.java)
                    list.add(data!!)
                }
                categoriesProductAdapter.differ.submitList(list)
                binding.recyclerViewCategoriesProduct.adapter = categoriesProductAdapter
            }
    }

    private fun arrangeItemsRecyclerView() {
        binding.recyclerViewCategoriesProduct.apply {
            val spanCount = 3
            val spacing = 50
            val includeEdge = false
            this.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }
}