package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.ecommerceapp.R
import com.project.ecommerceapp.adapters.CategoriesAdapter
import com.project.ecommerceapp.adapters.ProductAdapter
import com.project.ecommerceapp.databinding.FragmentHomeBinding
import com.project.ecommerceapp.db.ProductDatabase
import com.project.ecommerceapp.model.Categories
import com.project.ecommerceapp.model.Product
import com.project.ecommerceapp.repository.ProductRepository
import com.project.ecommerceapp.viewmodel.ProductViewModel
import com.project.ecommerceapp.viewmodel.ProductViewModelProviderFactory
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private lateinit var productAdapter: ProductAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ProductRepository(
            ProductDatabase.getInstance(requireActivity()).productDao(),
            ProductDatabase.getInstance(requireActivity()).favoriteProductDao()
        )
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)

        productAdapter = ProductAdapter(viewModel)

        setUpSlider()
        getCategories()
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
                productAdapter.differ.submitList(list)
                binding.recyclerViewAllProducts.adapter = productAdapter
            }
    }

    private fun getCategories() {
        val list = ArrayList<Categories>()
        fireStore.collection("Categories").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Categories::class.java)
                    list.add(data!!)
                }
                val categoryAdapter = CategoriesAdapter()
                categoryAdapter.differ.submitList(list)
                binding.recyclerViewCategories.adapter = categoryAdapter
            }
    }

    private fun setUpSlider() {
        val list = listOf<CarouselItem>(
            CarouselItem(R.drawable.i1),
            CarouselItem(R.drawable.i2),
        )
        binding.carousel.addData(list)
    }
}