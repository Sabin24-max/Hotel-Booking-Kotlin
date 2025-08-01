package com.example.hotelbooking.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.R
import com.example.hotelbooking.view.LoginActivity
import com.example.hotelbooking.view.admin.AdminDashboardActivity
import com.example.hotelbooking.view.UserDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { SplashBody() }
    }
}

@Composable
fun SplashBody() {
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(Unit) {
        delay(1800)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email.orEmpty()
        val next = when {
            user == null -> LoginActivity::class.java
            email == "admin@gmail.com" -> AdminDashboardActivity::class.java
            else -> UserDashboardActivity::class.java
        }
        context.startActivity(Intent(context, next))
        activity.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.95f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .shadow(22.dp, MaterialTheme.shapes.extraLarge)
                .padding(horizontal = 44.dp)
                .height(290.dp)
                .fillMaxWidth(0.82f),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 34.dp, vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.bookinglogo), // your logo
                    contentDescription = "Logo", modifier = Modifier.size(108.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Welcome to Hotel Booking", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
