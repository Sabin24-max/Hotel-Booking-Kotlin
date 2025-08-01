package com.example.hotelbooking.component

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.view.DashboardBody
import com.example.hotelbooking.view.LoginActivity
import com.example.hotelbooking.view.ProductDetailActivity
import com.example.hotelbooking.view.EditProfileActivity
import com.example.hotelbooking.view.UserHomeContent
import com.example.hotelbooking.view.UserProfileScreen

// Entry point, used in UserDashboardActivity
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeWithBottomNav() {
    data class BottomNavItem(val label: String, val icon: ImageVector)
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Browse Hotels", Icons.Filled.Search),
        BottomNavItem("My Bookings", Icons.Filled.Bookmarks),
        BottomNavItem("Profile", Icons.Filled.Person)
    )
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Hotel Booking") }) },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
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
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            AnimatedContent(targetState = selectedTab, label = "UserTab") { tab ->
                when (tab) {
                    0 -> UserHomeContent { selectedTab = 1 }
                    1 -> DashboardBody(
                        adminMode = false,
                        onLogout = { navToLogin(context) },
                        onViewProduct = { product ->
                            val intent = Intent(context, ProductDetailActivity::class.java)
                            intent.putExtra("productId", product.productId)
                            context.startActivity(intent)
                        }
                    )
                    2 -> UserBookingsScreen() // Working booking list below!
                    3 -> UserProfileScreen(
                        onEditProfile = {
                            context.startActivity(Intent(context, EditProfileActivity::class.java))
                        },
                        onLogout = { navToLogin(context) }
                    )
                }
            }
        }
    }
}

private fun navToLogin(context: Context) {
    // Always sign out from Firebase on logout:
    com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
    context.startActivity(Intent(context, LoginActivity::class.java))
    (context as? android.app.Activity)?.finish()
}
@Composable
fun UserBookingsScreen() {
    // Replace with real data from ViewModel if you have it
    val bookings = listOf(
        "Pokhara Lake Resort, 2025-09-01 to 2025-09-05",
        "Everest Palace, 2025-07-12 to 2025-07-16"
    )
    if (bookings.isEmpty()) {
        Column(
            Modifier.fillMaxSize().background(Color(0xFFF7F7F9)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = com.example.hotelbooking.R.drawable.hotel),
                contentDescription = "No Bookings",
                tint = Color.LightGray,
                modifier = Modifier.size(70.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("No Bookings Yet", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Text("Your booked stays will appear here.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    } else {
        LazyColumn(
            Modifier.fillMaxSize().background(Color(0xFFF7F7F9)).padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bookings) { booking ->
                Card(
                    Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(booking, Modifier.padding(18.dp), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

