package com.example.capstoneproject.ui.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class CameraViewModel : ViewModel() {
    val selectedImageUri: MutableLiveData<Uri> = MutableLiveData()
    val selectedImageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val selectedImageFile: MutableLiveData<File> = MutableLiveData()
    fun setImageUri(uri: Uri) {
        selectedImageUri.value = uri
    }
    fun setImageBitmap(bitmap: Bitmap) {
        selectedImageBitmap.postValue(bitmap)
    }
    fun setImageFile(file: File) {
        selectedImageFile.postValue(file)
    }
}
