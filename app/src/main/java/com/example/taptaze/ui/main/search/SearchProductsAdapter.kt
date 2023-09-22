package com.example.taptaze.ui.main.search

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
import com.example.taptaze.databinding.ItemSearchBinding

class SearchProductsAdapter(
    private val searchProductListener: SearchProductListener
) : ListAdapter<ProductUI, SearchProductsAdapter.SearchProductViewHolder>(SearchProductDiffCallBack()) {
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
        fun bind(product: ProductUI) = with(binding) {
            tvProductTitle.text = product.title
            ivProduct.loadImage(product.imageOne)

            var isFavorite = product.isFavorite

            ivProduct.setOnClickListener {
                searchProductListener.onProductClick(product.id)
            }

            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            }

            ivFavorite.setOnClickListener {
                isFavorite = !isFavorite
                ivFavorite.apply {
                    if (isFavorite) {
                        searchProductListener.onFavButtonClick(product)
                        ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    }
                }
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

    class SearchProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface SearchProductListener {
        fun onProductClick(id: Int)
        fun onFavButtonClick(product: ProductUI)
    }
}

