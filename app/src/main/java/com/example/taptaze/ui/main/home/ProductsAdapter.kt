package com.example.taptaze.ui.main.home

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.Product
import com.example.taptaze.databinding.ProductItemBinding

class ProductsAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ProductItemBinding,
        private val productListener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            tvProductTitle.text = product.title
            tvProductDesc.text = product.description
            ivProduct.loadImage(product.imageOne)

            ivProduct.setOnClickListener {
                productListener.onProductClick(product.id ?: 1)
            }

            fabAddToCart.setOnClickListener {
                productListener.onCartButtonClick(product.id!!.toInt())
            }

            if (product.saleState == true) {
                tvProductPrice.textSize = 14f
                tvProductSalePrice.visible()
                //bunlar düzeltilcek
                tvProductSalePrice.text = "₺${product.salePrice}"
                tvProductPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
            } else {
                tvProductPrice.text = "₺${product.price}"
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface ProductListener {
        fun onProductClick(id: Int)
        fun onCartButtonClick(id:Int)
    }
}

