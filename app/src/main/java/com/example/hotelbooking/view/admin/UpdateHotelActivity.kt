package com.example.hotelbooking.view.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.model.ProductModel

class UpdateHotelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UpdateHotelScreen(intent.getStringExtra("productId") ?: "") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHotelScreen(productId: String) {
    val activity = LocalActivity.current

    // In real code: Load hotel from ViewModel by productId
    val hotel = remember(productId) {
        // For demo just load by sample data; in real app pull from repo/viewmodel
        sampleHotels.find { it.productId == productId }
    }
    var name by remember { mutableStateOf(hotel?.name.orEmpty()) }
    var price by remember { mutableStateOf(hotel?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(hotel?.description.orEmpty()) }
    var imageUrl by remember { mutableStateOf(hotel?.imageUrl.orEmpty()) }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Update Hotel") }) }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(20.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Hotel Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Hotel Image Url") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                if (name.isBlank() || price.toDoubleOrNull() == null || imageUrl.isBlank()) {
                    Toast.makeText(activity, "Fill all required fields!", Toast.LENGTH_SHORT).show()
                } else {
                    // Here call ViewModel to update hotel in Firebase/DB; demo = finish
                    Toast.makeText(activity, "Hotel updated (demo)", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Save Hotel") }
        }
    }
}

// In shared file for demo:
val sampleHotels = listOf(
    ProductModel("h1", "Everest Palace", 5849.0, "5-star in Kathmandu. Rooftop pool, spa.", "https://images.unsplash.com/photo-1506744038136-46273834b3fb"),
    ProductModel("h2", "Pokhara Lake Resort", 3229.0, "Scenic lakeside hotel. Family rooms, deals.", "https://images.unsplash.com/photo-1529950846969-b43c6436c934"),
    ProductModel("h3", "Lumbini Heritage", 2899.0, "Peaceful stay near Lumbini. Free breakfast.", "https://images.unsplash.com/photo-1484154218962-a197022b5858")
)
