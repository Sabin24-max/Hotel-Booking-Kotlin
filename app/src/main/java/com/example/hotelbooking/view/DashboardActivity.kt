package com.example.hotelbooking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hotelbooking.model.ProductModel
import com.example.hotelbooking.R
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody(
    adminMode: Boolean,
    userEmail: String? = null,
    onLogout: (() -> Unit)? = null,
    onAddProduct: (() -> Unit)? = null,
    onEditProduct: ((ProductModel) -> Unit)? = null,
    onDeleteProduct: ((ProductModel) -> Unit)? = null,
    onViewProduct: ((ProductModel) -> Unit)? = null
) {
    // ViewModel data in real app; sample below for demo
    val products = remember { sampleHotels }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(if (adminMode) "Hotel Management" else "Explore Hotels", fontWeight = FontWeight.ExtraBold) },
                actions = {
                    IconButton(onClick = { /* Could navigate to profile or remove */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                    if (onLogout != null) {
                        IconButton(onClick = onLogout) {
                            Icon(Icons.Filled.Logout, contentDescription = "Logout")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp)
        ) {
            if (!adminMode && userEmail != null) {
                Text("Welcome, $userEmail", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
            }
            if (products.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hotels available.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(products) { product ->
                        HotelCardModern(
                            product = product,
                            onClick = { onViewProduct?.invoke(product) }
                        )
                    }
                }
            }
        }
    }
}

// Demo list
private val sampleHotels = listOf(
    ProductModel("h1", "Everest Palace", 5849.0, "5-star in Kathmandu. Rooftop pool, spa.", "https://images.unsplash.com/photo-1506744038136-46273834b3fb"),
    ProductModel("h2", "Pokhara Lake Resort", 3229.0, "Scenic lakeside hotel. Family rooms, deals.", "https://images.unsplash.com/photo-1529950846969-b43c6436c934"),
    ProductModel("h3", "Lumbini Heritage", 2899.0, "Peaceful stay near Lumbini. Free breakfast.", "https://images.unsplash.com/photo-1484154218962-a197022b5858")
)

@Composable
fun HotelCardModern(
    product: ProductModel,
    onClick: () -> Unit
) {
    val formattedPrice = remember(product.price) {
        "Rs. " + NumberFormat.getNumberInstance(Locale.US).format(product.price).replace(".00", "")
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 235.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl, contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                placeholder = painterResource(R.drawable.hotel),
                error = painterResource(R.drawable.hotel)
            )
            Column(Modifier.padding(18.dp)) {
                Text(product.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
                Text(formattedPrice, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(4.dp))
                Text(product.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 3)
            }
        }
    }
}


@Composable
fun HotelCardModern(
    product: ProductModel,
    adminMode: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    onMoreClick: (() -> Unit)? = null
) {
    val formattedPrice = remember(product.price) {
        "Rs. " + NumberFormat.getNumberInstance(Locale.US)
            .format(product.price)
            .replace(".00", "")
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 235.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    placeholder = painterResource(R.drawable.hotel),
                    error = painterResource(R.drawable.hotel)
                )
                if (adminMode && onMoreClick != null) {
                    IconButton(
                        onClick = onMoreClick,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More")
                    }
                }
            }
            Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        product.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    formattedPrice,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
            }
        }
    }
}
@Composable
fun UserHomeContent(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text("Welcome!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your next journey starts here. Book your stay today!", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(33.dp))
        Button(
            onClick = onGetStarted,
            shape = MaterialTheme.shapes.large
        ) { Text("Explore Hotels") }
    }
}

