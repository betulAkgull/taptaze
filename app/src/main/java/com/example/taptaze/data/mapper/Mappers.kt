package com.example.taptaze.data.mapper

import com.example.taptaze.data.model.Product
import com.example.taptaze.data.model.ProductEntity

fun Product.mapToProductEntity(): ProductEntity {
    return ProductEntity(
        id = id ?: 1,
        category = category,
        count = count,
        description = description,
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


fun ProductEntity.mapToProduct(): Product {
    return Product(
        id = id ?: 1,
        category = category,
        count = count,
        description = description,
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