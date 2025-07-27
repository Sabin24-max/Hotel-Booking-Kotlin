package com.example.hotelbooking.repository

import com.example.hotelbooking.model.ProductModel

interface ProductRepository {
    fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit)
}
