package com.example.hotelbooking.view.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminStatsScreen(onSeeDetails: () -> Unit = {}) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.padding(18.dp)
        ) {
            Column(
                modifier = Modifier.widthIn(min = 220.dp, max = 400.dp).padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bookings This Month", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(15.dp))
                Text("198", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = onSeeDetails,
                    shape = MaterialTheme.shapes.large
                ) { Text("See Details") }
            }
        }
    }
}
