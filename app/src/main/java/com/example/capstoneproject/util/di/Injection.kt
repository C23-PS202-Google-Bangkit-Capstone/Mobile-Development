package com.example.capstoneproject.util.di

import android.content.Context
import com.example.capstoneproject.util.api.ApiConfig
import com.example.capstoneproject.util.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository( apiService)
    }
}