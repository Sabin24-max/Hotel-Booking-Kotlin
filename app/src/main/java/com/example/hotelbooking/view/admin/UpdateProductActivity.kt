package com.example.hotelbooking.view.admin

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.repository.ProductRepositoryImpl
import com.example.hotelbooking.viewmodel.ProductViewModel

class UpdateProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpdateProductBody()
        }
    }
}

@Composable
fun UpdateProductBody() {
    val context = LocalContext.current
    val activity = context as? Activity
    val productId = activity?.intent?.getStringExtra("productId")

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    var pName by remember { mutableStateOf("") }
    var pPrice by remember { mutableStateOf("") }
    var pDesc by remember { mutableStateOf("") }

    val product by viewModel.singleProduct.observeAsState()

    // Fetch product from Firebase
    LaunchedEffect(productId) {
        if (!productId.isNullOrEmpty()) {
            viewModel.getProductById(productId)
        }
    }

    // Populate input fields with fetched product
    LaunchedEffect(product) {
        product?.let {
            pName = it.name
            pPrice = it.price.toString()
            pDesc = it.description
        }
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = pName,
                    onValueChange = { pName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = pPrice,
                    onValueChange = { pPrice = it },
                    label = { Text("Product Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = pDesc,
                    onValueChange = { pDesc = it },
                    label = { Text("Product Description") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val data = mapOf(
                            "name" to pName,
                            "price" to pPrice.toDoubleOrNull(),
                            "description" to pDesc,
                            "productId" to productId
                        )

                        if (productId != null && data["price"] != null) {
                            viewModel.updateProduct(productId, data) { success, message ->
                                if (success) {
                                    activity?.finish()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Product")
                }
            }
        }
    }
}
