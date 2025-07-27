package com.example.hotelbooking.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

object FirebaseUtils {

    fun uploadImageToCloudStorage(
        context: Context,
        imageUri: Uri,
        onResult: (String?) -> Unit
    ) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("product_images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    onResult(uri.toString())
                }.addOnFailureListener {
                    onResult(null)
                    Toast.makeText(context, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                onResult(null)
                Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }
}
