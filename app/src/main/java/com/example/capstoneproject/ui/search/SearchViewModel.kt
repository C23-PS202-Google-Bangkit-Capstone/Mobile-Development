package com.example.capstoneproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstoneproject.util.api.RecipesItem
import com.example.capstoneproject.util.repository.Repository

class SearchViewModel(private val repo: Repository) : ViewModel() {
    fun getStoriesSearch(recipeName: String): LiveData<PagingData<RecipesItem>> {
        return repo.getRecipesSearch(recipeName).cachedIn(viewModelScope)
    }
}