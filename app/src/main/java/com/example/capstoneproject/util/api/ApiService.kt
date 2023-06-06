package com.example.capstoneproject.util.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("/api/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("/api/getAdditionalData")
    suspend fun getAdditionalData(
        @Query("fruit_id") fruitId: Int,
    ): IntermezzoResponse

    @GET("/api/search")
    suspend fun getRecipesSearch(
        @Query("recipe_name") recipeName: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): RecipesResponse

    @GET("/api/getRecipeData")
    suspend fun getRecipes(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): RecipesResponse
}