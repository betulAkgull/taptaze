package com.example.taptaze.ui.main.search

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.Product
import com.example.taptaze.databinding.ItemSearchBinding

class SearchProductsAdapter(
    private val searchProductListener: SearchProductListener
) : ListAdapter<Product, SearchProductsAdapter.SearchProductViewHolder>(SearchProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder =
        SearchProductViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            searchProductListener
        )

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SearchProductViewHolder(
        private val binding: ItemSearchBinding,
        private val searchProductListener: SearchProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            tvProductTitle.text = product.title
            ivProduct.loadImage(product.imageOne)
            root.setOnClickListener {
                searchProductListener.onProductClick(product.id ?: 1)
            }
            if (product.saleState == true) {
                tvProductPrice.textSize = 14f
                tvProductPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
                tvProductSalePrice.visible()
                tvProductSalePrice.text = "₺${product.salePrice}"
            } else {
                tvProductPrice.text = "₺${product.price}"
            }
        }
    }

    class SearchProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface SearchProductListener {
        fun onProductClick(id: Int)
    }
}

