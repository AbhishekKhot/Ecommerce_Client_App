package com.project.ecommerceapp.repository

import com.project.ecommerceapp.db.FavoriteProductDao
import com.project.ecommerceapp.db.FavoriteProductModel
import com.project.ecommerceapp.db.ProductDao
import com.project.ecommerceapp.db.ProductModel

class ProductRepository(val db: ProductDao, val favoriteProductDao: FavoriteProductDao) {
    suspend fun insertProduct(product: ProductModel) =
        db.insertProduct(product)

    suspend fun insertFavoriteProduct(product: FavoriteProductModel) =
        favoriteProductDao.insertFavoriteProduct(product)

    suspend fun deleteProduct(product: ProductModel) =
        db.deleteProduct(product)

    suspend fun deleteFavoriteProduct(product: FavoriteProductModel) =
        favoriteProductDao.removeFavoriteProduct(product)

    fun getAllProduct() = db.getAllProduct()

    fun getAllFavoriteProduct() = favoriteProductDao.getAllFavoriteProduct()

    fun isExits(id: String) = db.isExits(id)

    fun isFavoriteProductExits(id: String) = favoriteProductDao.isFavoriteProductExits(id)

}