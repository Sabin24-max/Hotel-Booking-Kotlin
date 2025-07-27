package com.example.hotelbooking.repository

import com.example.hotelbooking.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)

    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit)

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)

    fun updateProfile(userID: String, data: MutableMap<String, Any?>, callback: (Boolean, String) -> Unit)

    fun getCurrentUser(): FirebaseUser?

    fun addUserToDatabase(userID: String, model: UserModel, callback: (Boolean, String) -> Unit)

    fun getUserByID(userID: String, callback: (UserModel?, Boolean, String) -> Unit)

    fun logout(callback: (Boolean, String) -> Unit)
}
