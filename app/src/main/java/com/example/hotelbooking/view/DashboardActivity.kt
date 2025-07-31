package com.example.hotelbooking.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hotelbooking.repository.ProductRepositoryImpl
import com.example.hotelbooking.view.admin.AddProductActivity
import com.example.hotelbooking.viewmodel.ProductViewModel
import com.example.hotelbooking.R
import com.example.hotelbooking.model.ProductModel

@Composable
fun DashboardBody(adminMode: Boolean) {
    val context = LocalContext.current
    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }
    val products by viewModel.allProducts.observeAsState(initial = emptyList())
    var deleteDialogProductId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.getAllProduct() }

    // ---- DELETE DIALOG FOR ADMIN ----
    if (adminMode && deleteDialogProductId != null) {
        AlertDialog(
            onDismissRequest = { deleteDialogProductId = null },
            title = { Text("Delete Hotel") },
            text = { Text("Are you sure you want to delete this hotel?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // TODO: Call viewModel.deleteProductById(deleteDialogProductId!!)
                        deleteDialogProductId = null
                    }
                ) { Text("Delete", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { deleteDialogProductId = null }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            if (adminMode) {
                FloatingActionButton(
                    onClick = {
                        context.startActivity(Intent(context, AddProductActivity::class.java))
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Hotel", tint = Color.White)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F6FA))
                .padding(padding)
        ) {
            Text(
                text = if (adminMode) "Admin Dashboard" else "Explore Hotels",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if (products.isEmpty()) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hotels available.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    items(products) { product ->
                        HotelCard(
                            product = product,
                            adminMode = adminMode,
                            onEdit = {
                                // TODO: Replace with navigation to edit activity
                                // context.startActivity(Intent(context, ...).putExtra("productId", product.productId))
                            },
                            onDelete = { deleteDialogProductId = product.productId }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HotelCard(
    product: ProductModel, // Replace with your actual product data class!
    adminMode: Boolean,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(enabled = !adminMode) {
                // Optional: Navigate to hotel detail/book page
            },
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // ---- HOTEL IMAGE ----
            AsyncImage(
                model = product.imageUrl, // ensure this field exists; adjust as needed
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFECECEC)),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                placeholder = painterResource(R.drawable.hotel), // fallback
                error = painterResource(R.drawable.hotel)
            )
            // ---- DETAILS ----
            Column(Modifier.padding(18.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Rs. ${product.price}",
                    fontSize = 18.sp,
                    color = Color(0xFF388E3C),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = product.description,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    maxLines = 3
                )
                // --- Amenities, ratings, etc. (Optional improvements) ---
                // Row(Modifier.padding(top = 8.dp)) { ... }
                // ---- ADMIN ACTIONS ----
                if (adminMode) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onEdit) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = onDelete) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}
