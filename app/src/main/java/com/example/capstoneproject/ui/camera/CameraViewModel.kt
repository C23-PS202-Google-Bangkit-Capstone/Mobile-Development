package com.example.capstoneproject.ui.camera

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.util.repository.Repository
import okhttp3.MultipartBody

class CameraViewModel(private val repo: Repository) : ViewModel() {
    val selectedImageUri: MutableLiveData<Uri> = MutableLiveData()
    val selectedImageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    fun setImageUri(uri: Uri) {
        selectedImageUri.value = uri
    }

    fun setImageBitmap(bitmap: Bitmap) {
        selectedImageBitmap.postValue(bitmap)
    }

    fun uploadFile(file: MultipartBody.Part) =
       repo.uploadImage(file)
}
