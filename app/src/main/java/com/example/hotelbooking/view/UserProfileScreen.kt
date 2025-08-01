package com.example.hotelbooking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun UserProfileScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF7FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text("Profile", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(18.dp))
        // You can add user info here once you have a user model!
        Button(onClick = onEditProfile, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth(0.65f)) {
            Text("Edit Profile")
        }
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedButton(
            onClick = onLogout,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {
            Text("Logout")
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text("Account details and booking history go here.", color = Color.Gray)
    }
}
