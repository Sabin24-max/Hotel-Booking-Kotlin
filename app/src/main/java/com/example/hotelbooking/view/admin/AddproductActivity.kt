package com.example.hotelbooking.view.admin

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hotelbooking.R
import com.example.hotelbooking.utils.ImageUtils
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.repository.ProductRepositoryImpl
import com.example.hotelbooking.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
    private lateinit var imageUtils: ImageUtils

    companion object {
        private var selectedImageUri: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        imageUtils = ImageUtils(this, this)
        imageUtils.registerLaunchers { uri ->
            selectedImageUri = uri
        }

        setContent {
            MaterialTheme {
                AddProductBody(
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imageUtils.launchImagePicker() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity
    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Hotel") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onPickImage() }
                    .background(
                        Color(0xFFF5F5F5),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(4.dp)
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.sabin),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.extraLarge),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                placeholder = { Text("Product Name") },
                label = { Text("Product Name") }
            )
            Spacer(Modifier.height(14.dp))
            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                placeholder = { Text("Product Description") },
                label = { Text("Description") },
                minLines = 3,
                maxLines = 8
            )
            Spacer(Modifier.height(14.dp))
            OutlinedTextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("Product Price") },
                label = { Text("Price") }
            )
            Spacer(Modifier.height(26.dp))
            Button(
                onClick = {
                    if (selectedImageUri != null
                        && productName.isNotBlank()
                        && (productPrice.toDoubleOrNull() ?: 0.0) > 0
                        && productDescription.isNotBlank()
                    ) {
                        viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                            if (imageUrl != null) {
                                val model = ProductModel(
                                    productId = "",
                                    name = productName,
                                    description = productDescription,
                                    price = productPrice.toDoubleOrNull() ?: 0.0,
                                    imageUrl = imageUrl
                                )
                                viewModel.addProduct(model) { success, message ->
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    if (success) activity?.finish()
                                }
                            } else {
                                Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please enter all information and pick an image", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Add Hotel")
            }
        }
    }
}
