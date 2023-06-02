package com.example.capstoneproject.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.util.repository.Repository
import com.example.capstoneproject.util.repository.UserModel

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return repository.getUserData()
    }

}