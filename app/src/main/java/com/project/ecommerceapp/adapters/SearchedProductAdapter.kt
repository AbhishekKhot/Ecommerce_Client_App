package com.project.ecommerceapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.SearchItemBinding
import com.project.ecommerceapp.model.Product

class SearchedProductAdapter :
    RecyclerView.Adapter<SearchedProductAdapter.SearchedProductViewHolder>() {

    inner class SearchedProductViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedProductViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvProductName.text = product.product_name
        holder.binding.tvProductDescription.text = product.product_description
        holder.binding.tvProductPrice.text = product.product_selling_price
        Glide.with(holder.itemView).load(product.product_cover_image).into(holder.binding.ivProduct)
        holder.binding.ivProduct.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productID", product.product_id)
            it.findNavController()
                .navigate(R.id.action_fragmentSearchProducts_to_fragmentProductDetails, bundle)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}