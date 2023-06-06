package com.example.capstoneproject.util.api

import com.google.gson.annotations.SerializedName

data class IntermezzoResponse(

    @field:SerializedName("message")
    val msg: String,

    @field:SerializedName("Description")
    val intermezzoResult: IntermezzoResult


)

data class IntermezzoResult(

    @field:SerializedName("detail")
    val description: String

    )