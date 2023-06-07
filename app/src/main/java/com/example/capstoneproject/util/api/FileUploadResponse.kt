package com.example.capstoneproject.util.api

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("imgName")
    val imgName: String,

    @field:SerializedName("type")
    val type: String
)
