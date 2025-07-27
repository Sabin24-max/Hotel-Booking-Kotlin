package com.example.hotelbooking.repository

import com.example.hotelbooking.model.ProductModel
import com.google.firebase.database.FirebaseDatabase

class ProductRepositoryImpl : ProductRepository {

    private val productRef = FirebaseDatabase.getInstance().getReference("products")

    override fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit) {
        val key = productRef.push().key ?: return callback(false, "Failed to generate key")
        product.productId = key

        productRef.child(key).setValue(product).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product added successfully")
            } else {
                callback(false, it.exception?.message ?: "Error adding product")
            }
        }
    }
}
