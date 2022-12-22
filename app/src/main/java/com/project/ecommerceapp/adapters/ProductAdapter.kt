package com.project.ecommerceapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.ProductItemBinding
import com.project.ecommerceapp.db.FavoriteProductModel
import com.project.ecommerceapp.model.Product
import com.project.ecommerceapp.viewmodel.ProductViewModel

class ProductAdapter(val viewModel:ProductViewModel) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            tvProductName.text = product.product_name
            tvProductPrice.text = "$" + product.product_selling_price
        }
        Glide.with(holder.itemView).load(product.product_cover_image)
            .placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)


        holder.binding.ivProduct.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productID", product.product_id)
            it.findNavController()
                .navigate(R.id.action_fragmentHome_to_fragmentProductDetails, bundle)
        }

        holder.binding.tvAddToFavorite.setOnClickListener {
            val data = FavoriteProductModel(
                product.product_id.toString(),
                product.product_name,
                product.product_cover_image,
                product.product_selling_price
            )
            viewModel.insertFavoriteProduct(data)
            Snackbar.make(it,"Product added to favorite list successfully",Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}