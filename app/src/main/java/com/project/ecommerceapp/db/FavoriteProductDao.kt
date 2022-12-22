package com.project.ecommerceapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteProductDao {
    @Insert
    suspend fun insertFavoriteProduct(product: FavoriteProductModel)

    @Delete
    suspend fun removeFavoriteProduct(product: FavoriteProductModel)

    @Query("SELECT*FROM FavoriteProducts")
    fun getAllFavoriteProduct(): LiveData<List<FavoriteProductModel>>

    @Query("SELECT*FROM FavoriteProducts WHERE productId=:id")
    fun isFavoriteProductExits(id: String): FavoriteProductModel
}