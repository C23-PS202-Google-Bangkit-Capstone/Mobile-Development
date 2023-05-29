package com.example.capstoneproject.ui.camera

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    val selectedImageUri: MutableLiveData<Uri> = MutableLiveData()
    val selectedImageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    fun setImageUri(uri: Uri) {
        selectedImageUri.value = uri
    }

    fun setImageBitmap(bitmap: Bitmap) {
        selectedImageBitmap.postValue(bitmap)
    }
}
