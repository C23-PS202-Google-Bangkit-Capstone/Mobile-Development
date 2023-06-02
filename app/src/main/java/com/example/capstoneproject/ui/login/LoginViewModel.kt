package com.example.capstoneproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.util.api.LoginRequest
import com.example.capstoneproject.util.repository.Repository
import com.example.capstoneproject.util.repository.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: Repository) : ViewModel() {
    fun postLogin(loginReq: LoginRequest) = repo.requestLogin(loginReq)
    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            repo.saveUserData(userModel)
        }
    }
}