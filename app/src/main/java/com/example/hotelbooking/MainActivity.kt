// MainActivity.kt
package com.example.hotelbooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, com.example.hotelbooking.component.SplashActivity::class.java))
        finish()
    }
}
