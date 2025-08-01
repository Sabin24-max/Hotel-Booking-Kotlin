package com.example.hotelbooking.model

data class UserModel(
    var userId: String = "",
    var email: String = "",
    var fullName: String = "",
    var phoneNumber: String = "",
    var selectedRole: String = ""
)
