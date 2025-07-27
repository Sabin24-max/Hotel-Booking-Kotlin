package com.example.hotelbooking.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.repository.ProductRepository
import com.example.hotelbooking.utils.FirebaseUtils
import com.google.firebase.database.*

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    // LiveData for all products
    private val _allProducts = MutableLiveData<List<ProductModel>>()
    val allProducts: LiveData<List<ProductModel>> get() = _allProducts

    // LiveData for a single product (nullable to handle not-found case)
    private val _singleProduct = MutableLiveData<ProductModel?>()
    val singleProduct: LiveData<ProductModel?> get() = _singleProduct

    // Loading and error states
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    /**
     * Upload product image to Firebase Storage.
     */
    fun uploadImage(context: Context, imageUri: Uri, onResult: (String?) -> Unit) {
        FirebaseUtils.uploadImageToCloudStorage(context, imageUri) { downloadUrl ->
            if (downloadUrl != null) {
                onResult(downloadUrl)
            } else {
                _errorMessage.postValue("Image upload failed.")
                onResult(null)
            }
        }
    }

    /**
     * Add a new product to Firebase Realtime Database.
     */
    fun addProduct(model: ProductModel, onResult: (Boolean, String) -> Unit) {
        _loading.postValue(true)
        repo.addProduct(model) { success, message ->
            _loading.postValue(false)
            if (!success) _errorMessage.postValue(message)
            onResult(success, message)
        }
    }

    /**
     * Fetch all products from Firebase.
     */
    fun getAllProduct() {
        _loading.postValue(true)
        val productRef = FirebaseDatabase.getInstance().getReference("products")

        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<ProductModel>()
                for (child in snapshot.children) {
                    val product = child.getValue(ProductModel::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                _allProducts.postValue(productList)
                _loading.postValue(false)
            }

            override fun onCancelled(error: DatabaseError) {
                _errorMessage.postValue("Failed to load products: ${error.message}")
                _loading.postValue(false)
            }
        })
    }

    /**
     * Fetch a single product by its ID.
     */
    fun getProductById(productId: String) {
        _loading.postValue(true)
        val ref = FirebaseDatabase.getInstance().getReference("products").child(productId)

        ref.get()
            .addOnSuccessListener { snapshot ->
                val product = snapshot.getValue(ProductModel::class.java)
                _singleProduct.postValue(product) // Now safely nullable
                if (product == null) {
                    _errorMessage.postValue("Product not found.")
                }
                _loading.postValue(false)
            }
            .addOnFailureListener { error ->
                _errorMessage.postValue("Failed to load product: ${error.message}")
                _loading.postValue(false)
            }
    }

    /**
     * Update an existing product by its ID.
     */
    fun updateProduct(productId: String, data: Map<String, Any?>, onResult: (Boolean, String) -> Unit) {
        _loading.postValue(true)
        val ref = FirebaseDatabase.getInstance().getReference("products").child(productId)

        ref.updateChildren(data)
            .addOnCompleteListener { task ->
                _loading.postValue(false)
                if (task.isSuccessful) {
                    onResult(true, "Product updated successfully.")
                } else {
                    val error = task.exception?.message ?: "Unknown error during update."
                    _errorMessage.postValue(error)
                    onResult(false, error)
                }
            }
            .addOnFailureListener { error ->
                _loading.postValue(false)
                val errorMsg = error.message ?: "Failed to update product."
                _errorMessage.postValue(errorMsg)
                onResult(false, errorMsg)
            }
    }
}
