package com.example.hotelbooking.view.admin

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.repository.ProductRepositoryImpl
import com.example.hotelbooking.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AddProductScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen() {
    val context = LocalContext.current
    val activity = context as? Activity
    val viewModel = remember { ProductViewModel(ProductRepositoryImpl()) }

    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Product") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(18.dp)
        ) {
            OutlinedTextField(
                value = productName, onValueChange = { productName = it },
                label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = productDescription, onValueChange = { productDescription = it },
                label = { Text("Description") }, minLines = 2, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = productPrice, onValueChange = { productPrice = it },
                label = { Text("Price") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = imageUrl, onValueChange = { imageUrl = it },
                label = { Text("Image URL") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    val price = productPrice.toDoubleOrNull()
                    if (productName.isBlank() || price == null || imageUrl.isBlank()) {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        val product = ProductModel("", productName, price, productDescription, imageUrl)
                        viewModel.addProduct(product) {
                            // No parameters! Success assumed; close activity
                            activity?.finish()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Add Hotel") }
        }
    }
}
