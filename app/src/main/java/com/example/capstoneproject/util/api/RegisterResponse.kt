package com.example.capstoneproject.util.api

data class RegisterResponse(
    val registerItem: List<RegisterItem>,
    val message: String
)

data class RegisterItem(
    val email: String,
    val password: String,
    val phoneNumber: String,
    val location: String
)

data class RegisterRequest(
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val location: String? = null
)