package com.example.taptaze.di

import com.example.taptaze.data.repository.ProductRepository
import com.example.taptaze.data.source.local.ProductDao
import com.example.taptaze.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(
        productService: ProductService,
        productDao: ProductDao
    ): ProductRepository =
        ProductRepository(productService, productDao)


}