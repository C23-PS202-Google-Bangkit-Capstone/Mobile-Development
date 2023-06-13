package com.example.capstoneproject.ui.camera


import androidx.lifecycle.ViewModel
import com.example.capstoneproject.util.repository.Repository
import okhttp3.MultipartBody

class CameraViewModel(private val repo: Repository) : ViewModel() {

    fun uploadFile(file: MultipartBody.Part) =
        repo.uploadImage(file)
}
