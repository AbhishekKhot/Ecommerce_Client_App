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
import com.project.ecommerceapp.databinding.CategorisedProductItemBinding
import com.project.ecommerceapp.model.Product

class SpecificCategoryProducts :
    RecyclerView.Adapter<SpecificCategoryProducts.SpecificCategoryViewHolder>() {
    inner class SpecificCategoryViewHolder(val binding: CategorisedProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificCategoryViewHolder {
        val binding = CategorisedProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecificCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificCategoryViewHolder, position: Int) {
        val product = differ.currentList[position]
        Glide.with(holder.itemView).load(product.product_cover_image)
            .placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)
        holder.binding.tvProductName.text = product.product_name

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productID", product.product_id)
            it.findNavController()
                .navigate(R.id.action_fragmentProductCategories_to_fragmentProductDetails, bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}