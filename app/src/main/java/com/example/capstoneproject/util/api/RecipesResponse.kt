package com.example.capstoneproject.util.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecipesResponse(
    @field:SerializedName("listRecipe")
    val listRecipes: List<RecipesItem>,
)

@Parcelize
data class RecipesItem(
    @field:SerializedName("id")
    val recipeId: Int,

    @field:SerializedName("name")
    val recipeName: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("description")
    val recipeDetail: String,

) : Parcelable
