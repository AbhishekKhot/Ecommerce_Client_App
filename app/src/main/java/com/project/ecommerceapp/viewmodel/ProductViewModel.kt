package com.project.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    fun insertProduct(product: ProductModel) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun deleteProduct(product: ProductModel) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun getAllProducts() = repository.getAllProduct()

    fun isExits(id: String) = repository.isExits(id)

}