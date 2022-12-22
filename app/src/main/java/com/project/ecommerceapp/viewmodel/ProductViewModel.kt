package com.project.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ecommerceapp.db.FavoriteProductModel
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    fun insertProduct(product: ProductModel) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun insertFavoriteProduct(product: FavoriteProductModel) = viewModelScope.launch {
        repository.insertFavoriteProduct(product)
    }


    fun deleteProduct(product: ProductModel) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun deleteFavoriteProduct(product: FavoriteProductModel) = viewModelScope.launch {
        repository.deleteFavoriteProduct(product)
    }

    fun getAllProducts() = repository.getAllProduct()

    fun getAllFavoriteProduct() = repository.getAllFavoriteProduct()

    fun isExits(id: String) = repository.isExits(id)

    fun isFavoriteProductExits(id: String) = repository.isFavoriteProductExits(id)

}