package com.example.taptaze.data.mapper

import com.example.taptaze.data.model.Product
import com.example.taptaze.data.model.ProductEntity
import com.example.taptaze.data.model.ProductUI

fun ProductUI.mapToProductEntity(): ProductEntity {
    return ProductEntity(
        category = category,
        count = count,
        description = description,
        id = id,
        imageOne = imageOne,
        imageThree = imageThree,
        imageTwo = imageTwo,
        price = price,
        rate = rate,
        salePrice = salePrice,
        saleState = saleState,
        title = title
    )
}

fun ProductEntity.mapToProductUI() : ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        imageThree = imageThree.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty(),
        isFavorite = true
    )
}

fun Product.mapToProductUI(isFavorite: Boolean): ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        imageThree = imageThree.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty(),
        isFavorite = isFavorite
    )
}
