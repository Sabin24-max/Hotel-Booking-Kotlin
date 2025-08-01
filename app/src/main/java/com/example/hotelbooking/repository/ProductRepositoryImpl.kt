package com.example.hotelbooking.repository

import com.example.hotelbooking.model.ProductModel

interface ProductRepository {
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getProductById(productId: String): ProductModel?
    suspend fun addProduct(product: ProductModel)
    suspend fun updateProduct(product: ProductModel)
    suspend fun deleteProduct(productId: String)
}

// Demo in-memory repo (replace with real Firebase for production)
class ProductRepositoryImpl : ProductRepository {
    private val products = mutableListOf(
        ProductModel("h1", "Everest Palace", 5849.0, "Luxury hotel in Kathmandu", "https://images.unsplash.com/photo-1506744038136-46273834b3fb"),
        ProductModel("h2", "Pokhara Lake Resort", 3229.0, "Scenic lakeside hotel", "https://images.unsplash.com/photo-1529950846969-b43c6436c934"),
        ProductModel("h3", "Lumbini Heritage", 2899.0, "Affordable stay near Lumbini", "https://images.unsplash.com/photo-1484154218962-a197022b5858")
    )

    override suspend fun getAllProducts(): List<ProductModel> = products.toList()
    override suspend fun getProductById(productId: String): ProductModel? = products.find { it.productId == productId }
    override suspend fun addProduct(product: ProductModel) {
        // For new product, generate a new ID (in real Firebase, push() or setValue())
        val id = product.productId.ifBlank { "h${products.size + 1}" }
        products.add(product.copy(productId = id))
    }
    override suspend fun updateProduct(product: ProductModel) {
        products.replaceAll { if (it.productId == product.productId) product else it }
    }
    override suspend fun deleteProduct(productId: String) {
        products.removeAll { it.productId == productId }
    }
}
