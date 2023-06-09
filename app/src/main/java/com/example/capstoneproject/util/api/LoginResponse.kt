package com.example.capstoneproject.util.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("user")
    val loginResult: LoginResult,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("msg")
    val message: String
)

data class LoginRequest(
    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("password")
    @Expose
    val password: String? = null
)

data class LoginResult(

    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("location")
    val location: String
)