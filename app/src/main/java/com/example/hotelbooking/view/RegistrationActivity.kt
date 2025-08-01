package com.example.hotelbooking.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.hotelbooking.model.UserModel
import com.example.hotelbooking.repository.UserRepositoryImpl
import com.example.hotelbooking.viewmodel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }
}

@Composable
fun RegistrationBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }
    val context = LocalContext.current
    val activity = context as? Activity

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }
    val roles = listOf("User", "Admin")
    var expanded by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.94f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .shadow(16.dp, shape = MaterialTheme.shapes.extraLarge)
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 28.dp, vertical = 28.dp)
                    .widthIn(min = 320.dp, max = 460.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = { activity?.finish() },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Back to Login", color = MaterialTheme.colorScheme.primary)
                }
                Text(
                    "Register",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    enabled = !loading
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    enabled = !loading
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    enabled = !loading
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    enabled = !loading
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    enabled = !loading
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Role Dropdown
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedRole,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Register As") },
                        shape = RoundedCornerShape(14.dp),
                        enabled = !loading,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        roles.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role) },
                                onClick = {
                                    selectedRole = role
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    enabled = !loading && fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank(),
                    onClick = {
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        loading = true
                        userViewModel.register(email, password) { success, message, userId ->
                            if (success) {
                                val user = UserModel(userId, email, fullName, phone, selectedRole)
                                userViewModel.addUserToDatabase(userId, user) { dbSuccess, dbMessage ->
                                    loading = false
                                    Toast.makeText(context, dbMessage, Toast.LENGTH_SHORT).show()
                                    if (dbSuccess) {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                        activity?.finish()
                                    }
                                }
                            } else {
                                loading = false
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(26.dp)
                        )
                    } else {
                        Text("Create Account")
                    }
                }
            }
        }
    }
}
