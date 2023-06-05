package com.example.capstoneproject.ui.register


import androidx.lifecycle.ViewModel
import com.example.capstoneproject.util.repository.Repository

class RegisterViewModel(private val repo: Repository) : ViewModel() {
    fun postRegister(email: String,username: String, password: String, phoneNumber: String, location: String) =
        repo.requestRegister(email, username, password, phoneNumber, location)
}