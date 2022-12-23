package com.project.ecommerceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
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
    ): View {
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

        binding.searchBar.setOnSearchActionListener(object:OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {

            }

            override fun onSearchConfirmed(text: CharSequence?) {
                val bundle = Bundle()
                bundle.putString("query",text.toString().trim())
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentSearchProducts,bundle)
            }

            override fun onButtonClicked(buttonCode: Int) {

            }

        })

//        binding.searchBar.addTextChangeListener(object:TextWatcher{
//            override fun beforeTextChanged(searchQuery: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(searchQuery: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(searchQuery: Editable?) {
//                val bundle = Bundle()
//                bundle.putString("query",searchQuery.toString().trim())
//                findNavController().navigate(R.id.action_fragmentHome_to_fragmentSearchProducts,bundle)
//            }
//        })


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
            CarouselItem(R.drawable.one),
            CarouselItem(R.drawable.two),
            CarouselItem(R.drawable.three),
            CarouselItem(R.drawable.four),
            CarouselItem(R.drawable.five))
        binding.carousel.addData(list)
    }
}