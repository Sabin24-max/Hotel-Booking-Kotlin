package com.example.hotelbooking.view.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class StatisticsDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StatisticsDetailScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsDetailScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Detailed Statistics") }) }
    ) { padding ->
        // Implement actual statistics detail UI here
        Text("Statistics Detail Page", modifier = Modifier.padding(padding))
    }
}
