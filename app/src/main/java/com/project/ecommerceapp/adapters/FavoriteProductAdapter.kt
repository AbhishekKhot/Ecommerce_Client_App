package com.project.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.FavoriteItemBinding
import com.project.ecommerceapp.db.FavoriteProductModel
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.viewmodel.ProductViewModel

class FavoriteProductAdapter(val viewModel: ProductViewModel) :
    RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductViewHolder>() {
    inner class FavoriteProductViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differCallback = object : DiffUtil.ItemCallback<FavoriteProductModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteProductModel,
            newItem: FavoriteProductModel
        ): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(
            oldItem: FavoriteProductModel,
            newItem: FavoriteProductModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvProductName.text = product.productName
        Glide.with(holder.itemView).load(product.productImage).placeholder(R.drawable.ic_image)
            .into(holder.binding.ivProduct)

        holder.binding.tvDelete.setOnClickListener {
            val data = FavoriteProductModel(
                product.productId,
                product.productName,
                product.productImage,
                product.productSellingPrice
            )
            viewModel.deleteFavoriteProduct(data)
            Snackbar.make(it, "Item Removed Successfully From Favorite List", Snackbar.LENGTH_SHORT)
                .show()
        }

        holder.binding.tvProductPrice.setOnClickListener {
            val data = ProductModel(
                product.productId,
                product.productName,
                product.productImage,
                product.productSellingPrice
            )

            if (viewModel.isExits(product.productId) != null) {
                holder.binding.tvProductPrice.text = "In Cart"
            } else {
                viewModel.insertProduct(data)
                Snackbar.make(it, "Item added to cart successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


}