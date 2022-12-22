package com.project.ecommerceapp.db

import android.content.Context
import androidx.room.*
import com.project.ecommerceapp.utils.Constants.DATABASE_NAME

@Database(entities = [ProductModel::class, FavoriteProductModel::class], version = 2)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun favoriteProductDao():FavoriteProductDao

    companion object {

        private var database: ProductDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ProductDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }
}