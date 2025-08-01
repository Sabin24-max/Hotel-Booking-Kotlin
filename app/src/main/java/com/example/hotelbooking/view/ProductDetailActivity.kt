package com.example.hotelbooking.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ProductDetailScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen() {
    // TODO: Fetch actual hotel/product data by productId (see intent extras)
    Scaffold(
        topBar = { TopAppBar(title = { Text("Hotel Details") }) }
    ) { padding ->
        Text(
            "Hotel details here (implement your own layout and data).",
            modifier = Modifier.padding(padding).padding(16.dp)
        )
    }
}
