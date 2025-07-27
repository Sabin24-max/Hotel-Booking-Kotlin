package com.example.hotelbooking.repository

import com.example.hotelbooking.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserRepositoryImpl : UserRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref: DatabaseReference = database.reference.child("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login successful")
            } else {
                callback(false, it.exception?.message ?: "Login failed")
            }
        }
    }

    override fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration successful", auth.currentUser?.uid ?: "")
            } else {
                callback(false, it.exception?.message ?: "Registration failed", "")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Reset email sent")
            } else {
                callback(false, it.exception?.message ?: "Failed to send reset email")
            }
        }
    }

    override fun updateProfile(userID: String, data: MutableMap<String, Any?>, callback: (Boolean, String) -> Unit) {
        ref.child(userID).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Profile updated")
            } else {
                callback(false, it.exception?.message ?: "Profile update failed")
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun addUserToDatabase(userID: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        ref.child(userID).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "User added to database")
            } else {
                callback(false, it.exception?.message ?: "Failed to add user")
            }
        }
    }

    override fun getUserByID(userID: String, callback: (UserModel?, Boolean, String) -> Unit) {
        ref.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                if (user != null) {
                    callback(user, true, "User found")
                } else {
                    callback(null, false, "User not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        return try {
            auth.signOut()
            callback(true, "Logout successful")
        } catch (e: Exception) {
            callback(false, e.message ?: "Logout failed")
        }
    }
}
