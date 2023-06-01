package com.example.capstoneproject.util.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.capstoneproject.util.api.ApiService
import com.example.capstoneproject.util.api.RecipesItem
import com.example.capstoneproject.util.api.RegisterRequest
import com.example.capstoneproject.util.api.RegisterResponse

class Repository(private val apiService: ApiService) {

    fun getRecipes(): LiveData<PagingData<RecipesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                SourcePaging(apiService)
            }
        ).liveData
    }

    fun requestRegister(email: String, password: String, phoneNumber: String, location: String)
            : LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.postRegister(
                    RegisterRequest(email, password, phoneNumber, location)
                )
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Signup", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }
}