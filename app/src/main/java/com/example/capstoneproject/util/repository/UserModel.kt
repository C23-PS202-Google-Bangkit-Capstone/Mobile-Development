package com.example.capstoneproject.util.repository

data class UserModel(
    var token: String,
    var userName: String,
    var location: String,
    var isLogin: Boolean
)