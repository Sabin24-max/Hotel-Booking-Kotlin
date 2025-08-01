package com.example.hotelbooking.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.R
import com.example.hotelbooking.view.admin.sampleHotels

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ProductDetailScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen() {
    // In real app: load product/hotel from intent or VM by ID
    val product = sampleHotels.firstOrNull() // For demo

    Scaffold(
        topBar = { TopAppBar(title = { Text(product?.name ?: "Hotel Details") }) }
    ) { padding ->
        if (product != null)
            Column(
                Modifier
                    .padding(padding)
                    .padding(18.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hotel),
                    contentDescription = "Hotel image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
                Spacer(Modifier.height(14.dp))
                Text(product.name, style = MaterialTheme.typography.headlineMedium)
                Text(product.description, Modifier.padding(vertical = 16.dp))
                Text("Price: Rs. ${product.price}", style = MaterialTheme.typography.titleMedium)
            }
        else
            Text("Hotel not found.", Modifier.padding(padding).padding(16.dp))
    }
}
