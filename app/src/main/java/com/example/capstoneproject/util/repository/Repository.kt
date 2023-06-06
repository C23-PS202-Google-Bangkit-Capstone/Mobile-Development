package com.example.capstoneproject.util.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.capstoneproject.util.api.ApiService
import com.example.capstoneproject.util.api.IntermezzoResponse
import com.example.capstoneproject.util.api.LoginRequest
import com.example.capstoneproject.util.api.LoginResponse
import com.example.capstoneproject.util.api.RecipesItem
import com.example.capstoneproject.util.api.RegisterRequest
import com.example.capstoneproject.util.api.RegisterResponse

class Repository(private val pref: UserPreferences, private val apiService: ApiService) {

    fun getRecipes(): LiveData<PagingData<RecipesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SourcePaging(apiService)
            }
        ).liveData
    }

    fun getRecipesSearch(recipeName: String): LiveData<PagingData<RecipesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SourcePagingSearch(apiService, recipeName)
            }
        ).liveData
    }

    fun getIntermezzo(id: Int): LiveData<Result<IntermezzoResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAdditionalData(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun requestRegister(
        username: String,
        email: String,
        password: String,
        phoneNumber: String,
        location: String
    )
            : LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.postRegister(
                    RegisterRequest(username,email, password, phoneNumber, location)
                )
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Signup", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun requestLogin(loginReq: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(loginReq)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUserData(user: UserModel) {
        pref.saveUser(user)
    }

    suspend fun logout() {
        pref.logout()
    }
}