package com.example.taptaze.ui.main.cart

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.Product
import com.example.taptaze.databinding.ItemCartBinding

class CartAdapter(
    private val cartListener: CartListener
) : ListAdapter<Product, CartAdapter.CartViewHolder>(CartDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            cartListener,
        )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val cartListener: CartListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            tvProductTitle.text = product.title
            tvProductDesc.text = product.description
            ivProduct.loadImage(product.imageOne)

            var productCount = 1

            ivProduct.setOnClickListener {
                cartListener.onCartClick(product.id ?: 1)
            }

            ivDelete.setOnClickListener {
                cartListener.onDeleteItemClick(product.id!!)
            }

            fabAdd.setOnClickListener {
                if (product.saleState == true) {
                    cartListener.onIncreaseItemClick(product.salePrice!!)
                } else {
                    cartListener.onIncreaseItemClick(product.price!!)
                }

                productCount++
                tvCount.text = productCount.toString()
            }

            fabRemove.setOnClickListener {
                if (productCount != 1) {
                    if (product.saleState == true) {
                        cartListener.onDecreaseItemClick(product.salePrice!!)
                    } else {
                        cartListener.onDecreaseItemClick(product.price!!)
                    }
                } else {
                    cartListener.onDeleteItemClick(product.id!!)
                }

                productCount--
                tvCount.text = productCount.toString()
            }

            if (product.saleState == true) {
                tvProductSalePrice.visible()
                tvProductSalePrice.textSize = 14f
                //bunlar düzeltilcek
                tvProductPrice.text = "₺${product.salePrice}"
                tvProductSalePrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))

            } else {
                tvProductPrice.text = "₺${product.price}"

            }
        }
    }

    class CartDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface CartListener {
        fun onCartClick(id: Int)
        fun onDeleteItemClick(id: Int)
        fun onIncreaseItemClick(price: Double)
        fun onDecreaseItemClick(price: Double)
    }
}

