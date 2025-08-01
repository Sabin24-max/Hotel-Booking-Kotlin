package com.example.hotelbooking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    var products by mutableStateOf<List<ProductModel>>(emptyList())
    var selectedProduct by mutableStateOf<ProductModel?>(null)
    var error by mutableStateOf<String?>(null)

    fun loadAllProducts() {
        viewModelScope.launch {
            try {
                products = repo.getAllProducts()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                selectedProduct = repo.getProductById(productId)
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun addProduct(product: ProductModel, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repo.addProduct(product)
                loadAllProducts()
                onSuccess()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun updateProduct(product: ProductModel, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repo.updateProduct(product)
                loadAllProducts()
                onSuccess()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteProduct(productId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repo.deleteProduct(productId)
                loadAllProducts()
                onSuccess()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}
