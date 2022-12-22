package com.project.ecommerceapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteProducts")
data class FavoriteProductModel(
    @PrimaryKey
    val productId: String,
    @ColumnInfo(name = "productName")
    val productName: String? = "",
    @ColumnInfo(name = "productImage")
    val productImage: String? = "",
    @ColumnInfo(name = "productSellingPrice")
    val productSellingPrice: String? = "",
)
