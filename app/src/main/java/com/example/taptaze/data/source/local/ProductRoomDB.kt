package com.example.taptaze.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taptaze.data.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {
    abstract fun productDao(): ProductDao
}