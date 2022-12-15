package com.project.ecommerceapp.repository

import com.project.ecommerceapp.db.ProductDao
import com.project.ecommerceapp.db.ProductModel

class ProductRepository(val db: ProductDao) {
    suspend fun insertProduct(product: ProductModel) =
        db.insertProduct(product)

    suspend fun deleteProduct(product: ProductModel) =
        db.deleteProduct(product)

    fun getAllProduct() = db.getAllProduct()

    fun isExits(id: String) = db.isExits(id)

}