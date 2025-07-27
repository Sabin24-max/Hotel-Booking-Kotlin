// SplashActivity.kt
package com.example.hotelbooking.component

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.R
import com.example.hotelbooking.view.admin.AdminDashboardActivity
import com.example.hotelbooking.view.LoginActivity
import com.example.hotelbooking.view.UserDashboardActivity
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
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", "") ?: ""

    LaunchedEffect(Unit) {
        delay(3000)
        val intent = when {
            email.isEmpty() -> Intent(context, LoginActivity::class.java)
            email == "admin@gmail.com" -> Intent(context, AdminDashboardActivity::class.java)
            else -> Intent(context, UserDashboardActivity::class.java)
        }
        context.startActivity(intent)
        activity.finish()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.bookinglogo), contentDescription = null)
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewSplash() {
    SplashBody()
}
