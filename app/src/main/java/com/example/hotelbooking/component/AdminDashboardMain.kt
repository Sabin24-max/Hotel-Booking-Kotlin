package com.example.hotelbooking.component

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.hotelbooking.view.DashboardBody
import com.example.hotelbooking.view.LoginActivity
import com.example.hotelbooking.view.admin.AddHotelActivity
import com.example.hotelbooking.view.admin.UpdateHotelActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardMain() {
    data class TabItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
    val tabs = listOf(
        TabItem("Hotels", Icons.AutoMirrored.Filled.List),
        TabItem("Bookings", Icons.Filled.ReceiptLong),
        TabItem("Users", Icons.Filled.People),
        TabItem("Stats", Icons.Filled.BarChart)
    )
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Admin Panel") }) }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                tabs.forEachIndexed { i, item ->
                    Tab(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        text = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = item.label) }
                    )
                }
            }
            AnimatedContent(targetState = selectedTab, label = "TabAnimation") { currTab ->
                when (currTab) {
                    0 -> DashboardBody(
                        adminMode = true,
                        onLogout = {
                            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                            context.startActivity(Intent(context, LoginActivity::class.java))
                            (context as? android.app.Activity)?.finish()
                        },
                        onAddProduct = { context.startActivity(Intent(context, AddHotelActivity::class.java)) },
                        onEditProduct = { hotel ->
                            val intent = Intent(context, UpdateHotelActivity::class.java)
                            intent.putExtra("productId", hotel.productId)
                            context.startActivity(intent)
                        },
                        onDeleteProduct = { hotel -> /* TODO: call ViewModel to delete */ },
                        onViewProduct = { hotel ->
                            // ADMIN: clicking a hotel opens the UPDATE screen for editing!
                            val intent = Intent(context, UpdateHotelActivity::class.java)
                            intent.putExtra("productId", hotel.productId)
                            context.startActivity(intent)
                        }
                    )
                    1 -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Bookings Management", style = MaterialTheme.typography.headlineMedium) }
                    2 -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("User Management", style = MaterialTheme.typography.headlineMedium) }
                    3 -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("See Stats here", style = MaterialTheme.typography.headlineMedium) }
                }
            }
        }
    }
}
