package com.example.hotelbooking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.viewmodel.ProductViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    isAdmin: Boolean,
    onAdd: (() -> Unit)? = null,
    onEdit: ((ProductModel) -> Unit)? = null,
    onDelete: ((ProductModel) -> Unit)? = null,
    onDetail: ((ProductModel) -> Unit)? = null
) {
    LaunchedEffect(Unit) { viewModel.loadAllProducts() }
    val products = viewModel.products

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isAdmin) "Admin Dashboard" else "Explore Hotels") },
                actions = {
                    if (isAdmin && onAdd != null) {
                        IconButton(onClick = { onAdd() }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Hotel")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (products.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hotels available.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF8F9FA))
                    .padding(horizontal = 12.dp),
            ) {
                products.forEach { product ->
                    HotelCardM3(
                        product = product,
                        isAdmin = isAdmin,
                        onEdit = { onEdit?.invoke(product) },
                        onDelete = { onDelete?.invoke(product) },
                        onClick = { onDetail?.invoke(product) }
                    )
                    Spacer(Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
fun HotelCardM3(
    product: ProductModel,
    isAdmin: Boolean,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val formattedPrice = remember(product.price) {
        "Rs. " + NumberFormat.getNumberInstance(Locale.US).format(product.price).replace(".00", "")
    }
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 210.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(product.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
                    if (isAdmin) {
                        IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, null) }
                        IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                    }
                }
                Text(formattedPrice, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(8.dp))
                Text(product.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            }
        }
    }
}
