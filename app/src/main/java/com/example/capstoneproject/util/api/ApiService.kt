package com.example.capstoneproject.util.api

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

}