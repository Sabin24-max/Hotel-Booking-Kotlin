package com.example.hotelbooking.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hotelbooking.view.DashboardBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardMain() {
    data class TabItem(val label: String, val icon: ImageVector)
    val tabs = listOf(
        TabItem("Statistics", Icons.Filled.BarChart),
        TabItem("Products", Icons.AutoMirrored.Filled.List)
    )
    var selectedTab by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Admin Dashboard") }) }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { i, item ->
                    Tab(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        text = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = item.label) }
                    )
                }
            }
            when (selectedTab) {
                0 -> AdminStatsScreen()
                1 -> DashboardBody(adminMode = true) // admin sees management UI here!
            }
        }
    }
}

@Composable
fun AdminStatsScreen() {
    // Replace with charts, totals, etc
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Admin Statistics (chart, metrics, KPIs, etc.)", style = MaterialTheme.typography.bodyLarge)
    }
}
