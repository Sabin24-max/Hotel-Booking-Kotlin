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

class AddHotelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AddHotelScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHotelScreen() {
    val activity = LocalActivity.current
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Hotel") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(18.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Hotel Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Hotel Image Url") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                if (name.isBlank() || price.toDoubleOrNull() == null || imageUrl.isBlank()) {
                    Toast.makeText(activity, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
                } else {
                    // Here call your ViewModel to add
                    // For demo, just exit:
                    Toast.makeText(activity, "Hotel Added (demo)", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Hotel")
            }
        }
    }
}
