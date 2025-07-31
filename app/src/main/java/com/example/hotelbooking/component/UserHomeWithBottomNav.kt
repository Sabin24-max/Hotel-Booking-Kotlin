package com.example.hotelbooking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.view.DashboardBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeWithBottomNav() {
    data class BottomNavItem(val label: String, val icon: ImageVector)
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Shop", Icons.Filled.ShoppingCart),
        BottomNavItem("Account", Icons.Filled.Person)
    )
    var selectedTab by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hotel Booking") }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { i, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedTab == i,
                        onClick = { selectedTab = i }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            when (selectedTab) {
                0 -> UserHomeContent()
                1 -> DashboardBody(adminMode = false) // uses your product list for shop/browse
                2 -> UserProfileScreen()
            }
        }
    }
}

@Composable
fun UserHomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text("Welcome!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Book your best stay with us.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF7FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text("Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields here (email, booking history, logout, etc.)
        Text("Account details and booking history go here.", color = Color.Gray)
    }
}
