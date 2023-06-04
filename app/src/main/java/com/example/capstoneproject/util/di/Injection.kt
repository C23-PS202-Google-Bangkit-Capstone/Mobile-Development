package com.example.capstoneproject.util.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.capstoneproject.util.api.ApiConfig
import com.example.capstoneproject.util.repository.Repository
import com.example.capstoneproject.util.repository.UserPreferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository(preferences, apiService)
    }
}