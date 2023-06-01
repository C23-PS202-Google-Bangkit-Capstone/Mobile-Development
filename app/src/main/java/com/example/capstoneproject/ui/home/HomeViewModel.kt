package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstoneproject.util.api.RecipesItem
import com.example.capstoneproject.util.repository.Repository

class HomeViewModel(private val repo: Repository) : ViewModel() {

    fun getStories(): LiveData<PagingData<RecipesItem>> {
        return repo.getRecipes().cachedIn(viewModelScope)
    }
}