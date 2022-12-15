package com.project.ecommerceapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.ecommerceapp.R
import com.project.ecommerceapp.databinding.CartItemBinding
import com.project.ecommerceapp.db.ProductModel
import com.project.ecommerceapp.viewmodel.ProductViewModel

class CartAdapter(val viewModel: ProductViewModel) :
    RecyclerView.Adapter<CartAdapter.CartProductViewHolder>() {

    inner class CartProductViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvProductName.text = product.productName
        holder.binding.tvProductPrice.text = "$" + product.productSellingPrice
        Glide.with(holder.itemView).load(product.productImage).placeholder(R.drawable.ic_image)
            .into(holder.binding.ivProduct)

        holder.binding.tvDelete.setOnClickListener {
            val data = ProductModel(
                product.productId,
                product.productName,
                product.productImage,
                product.productSellingPrice
            )
            viewModel.deleteProduct(data)
            Snackbar.make(it, "Item Removed Successfully From Cart", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}