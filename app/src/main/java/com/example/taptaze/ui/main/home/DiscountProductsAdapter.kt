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
import com.example.taptaze.data.model.ProductUI
import com.example.taptaze.databinding.ProductItemBinding

class DiscountProductsAdapter(
    private val discountProductListener: DiscountProductListener
) : ListAdapter<ProductUI, DiscountProductsAdapter.DiscountProductViewHolder>(
    DiscountProductDiffCallBack()
) {
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
        fun bind(product: ProductUI) = with(binding) {
            tvProductTitle.text = product.title
            tvProductDesc.text = product.description
            ivProduct.loadImage(product.imageOne)


            var isFavorite = product.isFavorite

            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            }

            ivFavorite.setOnClickListener {
                isFavorite = !isFavorite
                ivFavorite.apply {
                    if (isFavorite) {
                        ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    }
                }
                productListener.onFavButtonClick(product)
            }

            ivProduct.setOnClickListener {
                productListener.onProductClick(product.id)
            }

            fabAddToCart.setOnClickListener {
                productListener.onCartButtonClick(product.id)
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

    class DiscountProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface DiscountProductListener {
        fun onProductClick(id: Int)
        fun onCartButtonClick(id: Int)
        fun onFavButtonClick(product: ProductUI) {

        }
    }
}

