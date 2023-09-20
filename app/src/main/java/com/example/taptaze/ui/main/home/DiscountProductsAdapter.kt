package com.example.taptaze.ui.main.home

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taptaze.R
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.Product
import com.example.taptaze.databinding.ProductItemBinding

class DiscountProductsAdapter(
    private val discountProductListener: DiscountProductListener
) : ListAdapter<Product, DiscountProductsAdapter.DiscountProductViewHolder>(DiscountProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountProductViewHolder =
        DiscountProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            discountProductListener
        )

    override fun onBindViewHolder(holder: DiscountProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DiscountProductViewHolder(
        private val binding: ProductItemBinding,
        private val productListener: DiscountProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            tvProductTitle.text = product.title
            tvProductDesc.text = product.description
            ivProduct.loadImage(product.imageOne)

            ivProduct.setOnClickListener {
                productListener.onProductClick(product.id ?: 1)
            }

            ivFavorite.setOnClickListener {
                productListener.onFavButtonClick(product)
                ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            }

            fabAddToCart.setOnClickListener {
                productListener.onCartButtonClick(product.id!!.toInt())
            }

            if (product.saleState == true) {
                tvProductPrice.textSize = 12f
                tvProductSalePrice.visible()
                //bunlar düzeltilcek
                tvProductSalePrice.text = "₺${product.salePrice}"
                tvProductPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
            } else {
                tvProductPrice.text = "₺${product.price}"
            }
        }
    }

    class DiscountProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface DiscountProductListener {
        fun onProductClick(id: Int)
        fun onCartButtonClick(id:Int)
        fun onFavButtonClick(product: Product) {

        }
    }
}

