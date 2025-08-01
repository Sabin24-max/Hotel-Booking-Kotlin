package com.example.hotelbooking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelbooking.model.UserModel
import com.example.hotelbooking.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.register(email, password, callback)
    }
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }
    fun logout(callback: (Boolean, String) -> Unit) {
        repo.logout(callback)
    }
    fun getCurrentUser(): FirebaseUser? = repo.getCurrentUser()

    fun addUserToDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, model, callback)
    }
    fun updateProfile(userId: String, data: MutableMap<String, Any?>, callback: (Boolean, String) -> Unit) {
        repo.updateProfile(userId, data, callback)
    }

    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> get() = _user

    fun getUserById(userId: String) {
        repo.getUserByID(userId) { fetchedUser, success, _ ->
            _user.postValue(if (success) fetchedUser else null)
        }
    }
}
