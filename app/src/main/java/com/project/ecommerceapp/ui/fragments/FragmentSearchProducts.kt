package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.ecommerceapp.adapters.SearchedProductAdapter
import com.project.ecommerceapp.databinding.FragmentSearchProductsBinding
import com.project.ecommerceapp.model.Product
import java.util.*
import kotlin.collections.ArrayList

class FragmentSearchProducts : Fragment() {
    private var _binding: FragmentSearchProductsBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val args: FragmentSearchProductsArgs by navArgs()
    private lateinit var searchedProductAdapter: SearchedProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchedProductAdapter = SearchedProductAdapter()
        getAllProducts()
    }


    private fun getAllProducts() {
        val list = ArrayList<Product>()
        fireStore.collection("Products").get()
            .addOnSuccessListener {
                for (doc in it.documents) {
                    val data = doc.toObject(Product::class.java)
                    list.add(data!!)
                }
                sortProducts(list)
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun sortProducts(productArrayList: ArrayList<Product>) {
        val sortedProductList = ArrayList<Product>()
        productArrayList.forEach {
            if (it.product_name?.lowercase(Locale.getDefault())!!
                    .contains(args.query.lowercase(Locale.getDefault())) ||
                it.product_description?.lowercase(Locale.getDefault())!!
                    .contains(args.query.lowercase(Locale.getDefault()))
            ) {
                sortedProductList.add(it)
            }
        }
        searchedProductAdapter.differ.submitList(sortedProductList)
        binding.recyclerViewSearchProduct.adapter = searchedProductAdapter
    }
}