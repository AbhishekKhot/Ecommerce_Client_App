package com.project.ecommerceapp.db

import android.content.Context
import androidx.room.*

@Database(entities = [ProductModel::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        private var database: ProductDatabase? = null
        private val DATABASE_NAME = "EDatabase"

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