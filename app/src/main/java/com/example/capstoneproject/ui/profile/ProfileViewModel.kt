package com.example.capstoneproject.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstoneproject.util.api.RecipesItem
import com.example.capstoneproject.util.repository.Repository
import com.example.capstoneproject.util.repository.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return repository.getUserData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getStoriesRecommendation(recipeLocation: String): LiveData<PagingData<RecipesItem>> {
        return repository.getRecipesRecommendation(recipeLocation).cachedIn(viewModelScope)
    }
}