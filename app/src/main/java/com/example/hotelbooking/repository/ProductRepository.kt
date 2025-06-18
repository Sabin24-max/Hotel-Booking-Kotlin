package com.example.hotelbooking.repository

import android.content.Context
import android.net.Uri
import com.example.hotelbooking.model.ProductModel


interface ProductRepository {

    /*
    success : true,
    message : "product added successfully"
    */
    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context,uri: Uri): String?
    fun addProduct(model: ProductModel,
                   callback: (Boolean, String)-> Unit)

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    )

    /*
  success : true,
  message : "product fetched successfully"
   */
    fun getProductById(
        productId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    )

    fun getAllProduct(callback: (Boolean, String,
                                 List<ProductModel?>) -> Unit)


    //present - true
    //absent - false
//    fun attendance(name:String,callback: (Boolean) -> Unit)

}